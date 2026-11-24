package cl.duoc.test;

import cl.duoc.test.model.AppUser;
import cl.duoc.test.model.Course;
import cl.duoc.test.model.Role;
import cl.duoc.test.model.Subject;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.SubjectRepository;
import cl.duoc.test.repository.UserRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedCatalog(CourseRepository courses, SubjectRepository subjects,
                                  UserRepository users, PasswordEncoder encoder) {
        return args -> {
            if (courses.count() == 0) {
                courses.saveAll(List.of(new Course("5.\u00ba B\u00e1sico A"), new Course("6.\u00ba B\u00e1sico A"),
                        new Course("7.\u00ba B\u00e1sico A"), new Course("8.\u00ba B\u00e1sico A")));
            }
            if (subjects.count() == 0) {
                subjects.saveAll(List.of(new Subject("Matem\u00e1ticas"), new Subject("Lenguaje y Comunicaci\u00f3n"),
                        new Subject("Ciencias Naturales"), new Subject("Historia y Geograf\u00eda")));
            }

            seedUser(courses, users, encoder, "Mar\u00eda", "Gonz\u00e1lez", "docente@duoc.cl", Role.PROFESOR, null);
            seedUser(courses, users, encoder, "Carlos", "Rodr\u00edguez", "docente2@duoc.cl", Role.PROFESOR, null);
            seedUser(courses, users, encoder, "Ignacia", "Soto", "estudiante@duoc.cl", Role.ALUMNO, "5.\u00ba B\u00e1sico A");
            seedUser(courses, users, encoder, "Lucas", "P\u00e9rez", "estudiante2@duoc.cl", Role.ALUMNO, "8.\u00ba B\u00e1sico A");
        };
    }

    private void seedUser(CourseRepository courses, UserRepository users, PasswordEncoder encoder, String firstName, String lastName,
                          String email, Role role, String courseName) {
        if (users.findByEmailIgnoreCase(email).isPresent()) return;
        Course course = courseName == null ? null : courses.findByName(courseName).orElseThrow();
        users.save(new AppUser(firstName, lastName, email, encoder.encode("123456"), role, course));
    }
}
