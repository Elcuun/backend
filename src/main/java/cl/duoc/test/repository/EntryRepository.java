package cl.duoc.test.repository;
import cl.duoc.test.model.LogbookEntry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
public interface EntryRepository extends JpaRepository<LogbookEntry, Long> {
    List<LogbookEntry> findByLogbookId(Long logbookId);
    List<LogbookEntry> findByStudentNameContainingIgnoreCase(String query);
    List<LogbookEntry> findByStudentId(String studentId);
    void deleteByLogbookId(Long logbookId);
}
