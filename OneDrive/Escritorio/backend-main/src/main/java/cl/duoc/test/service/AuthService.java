package cl.duoc.test.service;

import cl.duoc.test.api.Dtos.AuthResponse;
import cl.duoc.test.api.Dtos.LoginRequest;
import cl.duoc.test.api.Dtos.UserRequest;
import cl.duoc.test.api.Dtos.UserResponse;
import cl.duoc.test.api.TokenService;
import cl.duoc.test.model.AppUser;
import cl.duoc.test.model.Course;
import cl.duoc.test.model.Role;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository users;
    private final CourseRepository courses;
    private final PasswordEncoder encoder;
    private final TokenService tokens;

    public AuthService(UserRepository users, CourseRepository courses, PasswordEncoder encoder, TokenService tokens) {
        this.users = users;
        this.courses = courses;
        this.encoder = encoder;
        this.tokens = tokens;
    }

    @Transactional
    public AuthResponse register(UserRequest r) {
        String email = r.email().trim().toLowerCase();
        if (users.findByEmailIgnoreCase(email).isPresent()) {
            throw new IllegalArgumentException("Ese correo ya está registrado");
        }
        Course course = null;
        if (r.role() == Role.ALUMNO) {
            if (r.courseId() == null) {
                throw new IllegalArgumentException("El estudiante debe seleccionar un curso");
            }
            course = courses.findById(r.courseId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        } else if (r.courseId() != null) {
            throw new IllegalArgumentException("Solo los estudiantes pueden asociarse a un curso");
        }
        AppUser user = users.save(new AppUser(
                r.firstName().trim(),
                r.lastName().trim(),
                email,
                encoder.encode(r.password()),
                r.role(),
                course
        ));
        return new AuthResponse(UserResponse.from(user), tokens.issue(user.getId()));
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest r) {
        AppUser u = users.findByEmailIgnoreCase(r.email().trim())
                .orElseThrow(() -> new IllegalArgumentException("Correo o contraseña incorrectos"));
        if (!encoder.matches(r.password(), u.getPasswordHash()) || u.getRole() != r.role()) {
            throw new IllegalArgumentException("Correo, contraseña o perfil incorrectos");
        }
        return new AuthResponse(UserResponse.from(u), tokens.issue(u.getId()));
    }
}
