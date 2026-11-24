package cl.duoc.test.repository;
import cl.duoc.test.model.Logbook;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LogbookRepository extends JpaRepository<Logbook, Long> {
    List<Logbook> findByCourseIdAndSubjectId(Long courseId, Long subjectId);
}
