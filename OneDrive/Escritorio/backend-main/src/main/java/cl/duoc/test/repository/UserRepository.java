package cl.duoc.test.repository;
import cl.duoc.test.model.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser, String> { Optional<AppUser> findByEmailIgnoreCase(String email); }
