package cl.duoc.test.api;
import cl.duoc.test.api.Dtos.NamedResponse;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.SubjectRepository;
import java.util.List;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class CatalogController {
 private final CourseRepository courses; private final SubjectRepository subjects;
 public CatalogController(CourseRepository c, SubjectRepository s) { courses=c; subjects=s; }
 @GetMapping("/courses") public List<NamedResponse> courses() { return courses.findAll().stream().map(NamedResponse::from).toList(); }
 @GetMapping("/subjects") public List<NamedResponse> subjects() { return subjects.findAll().stream().map(NamedResponse::from).toList(); }
}
