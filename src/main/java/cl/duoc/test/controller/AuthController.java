package cl.duoc.test.controller;
import cl.duoc.test.api.Dtos.*;
import cl.duoc.test.api.TokenService;
import cl.duoc.test.model.*;
import cl.duoc.test.repository.*;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final CourseRepository courses; private final PasswordEncoder encoder;
 private final TokenService tokens;
 public AuthController(UserRepository u, CourseRepository c, PasswordEncoder e,TokenService tokens) { users=u; courses=c; encoder=e; this.tokens=tokens; }
 @PostMapping("/register") public AuthResponse register(@Valid @RequestBody UserRequest r) {
  String email=r.email().trim().toLowerCase();
  if (users.findByEmailIgnoreCase(email).isPresent()) throw new IllegalArgumentException("Ese correo ya estÃ¡ registrado");
  Course course=null;
  if (r.role()==Role.ALUMNO) { if (r.courseId()==null) throw new IllegalArgumentException("El estudiante debe seleccionar un curso"); course=courses.findById(r.courseId()).orElseThrow(()->new IllegalArgumentException("Curso no encontrado")); }
  else if (r.courseId()!=null) throw new IllegalArgumentException("Solo los estudiantes pueden asociarse a un curso");
  AppUser user=users.save(new AppUser(r.firstName().trim(),r.lastName().trim(),email,encoder.encode(r.password()),r.role(),course));
  return new AuthResponse(UserResponse.from(user),tokens.issue(user.getId()));
 }
 @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r) {
  AppUser u=users.findByEmailIgnoreCase(r.email().trim()).orElseThrow(()->new IllegalArgumentException("Correo o contraseÃ±a incorrectos"));
  if (!encoder.matches(r.password(),u.getPasswordHash()) || u.getRole()!=r.role()) throw new IllegalArgumentException("Correo, contraseÃ±a o perfil incorrectos");
  return new AuthResponse(UserResponse.from(u),tokens.issue(u.getId()));
 }
}
