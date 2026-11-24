package cl.duoc.test.api;
import cl.duoc.test.model.AppUser;
import cl.duoc.test.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
@Component public class BearerTokenFilter extends OncePerRequestFilter {
 private final TokenService tokens; private final UserRepository users;
 public BearerTokenFilter(TokenService t,UserRepository u){tokens=t;users=u;}
 @Override protected void doFilterInternal(HttpServletRequest req,HttpServletResponse res,FilterChain chain)throws ServletException,IOException{
  String header=req.getHeader("Authorization");
  if(header!=null&&header.startsWith("Bearer ")){
   String userId=tokens.userId(header.substring(7));
   if(userId!=null){AppUser u=users.findById(userId).orElse(null);if(u!=null){var auth=new UsernamePasswordAuthenticationToken(u.getId(),null,List.of(new SimpleGrantedAuthority("ROLE_"+u.getRole().name())));SecurityContextHolder.getContext().setAuthentication(auth);}}
  }
  chain.doFilter(req,res);
 }
}
