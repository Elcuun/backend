package cl.duoc.test.repository;
import cl.duoc.test.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SubjectRepository extends JpaRepository<Subject, Long> {}
