package cl.duoc.test;
import cl.duoc.test.model.Course;
import cl.duoc.test.model.Subject;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.SubjectRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration public class DataSeeder {
 @Bean CommandLineRunner seedCatalog(CourseRepository courses,SubjectRepository subjects){return args->{
  if(courses.count()==0) courses.saveAll(java.util.List.of(new Course("5.º Básico A"),new Course("6.º Básico A"),new Course("7.º Básico A"),new Course("8.º Básico A")));
  if(subjects.count()==0) subjects.saveAll(java.util.List.of(new Subject("Matemáticas"),new Subject("Lenguaje y Comunicación"),new Subject("Ciencias Naturales"),new Subject("Historia y Geografía")));
 };}
}
