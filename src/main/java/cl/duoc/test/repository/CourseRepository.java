package cl.duoc.test.repository;
import cl.duoc.test.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CourseRepository extends JpaRepository<Course, Long> {}
