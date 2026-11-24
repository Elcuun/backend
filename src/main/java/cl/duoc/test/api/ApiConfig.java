package cl.duoc.test.api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;
@Configuration @EnableMethodSecurity public class ApiConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain security(HttpSecurity http,BearerTokenFilter filter)throws Exception{
  return http.csrf(csrf->csrf.disable()).cors(cors->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**","/api/courses","/api/subjects").permitAll().anyRequest().authenticated())
   .addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(){CorsConfiguration c=new CorsConfiguration();c.setAllowedOrigins(List.of("http://localhost:3000","http://localhost:8081"));c.setAllowedMethods(List.of("GET","POST","PATCH","DELETE","OPTIONS"));c.setAllowedHeaders(List.of("Content-Type","Authorization"));UrlBasedCorsConfigurationSource s=new UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/api/**",c);return s;}
}
