package cl.duoc.test.controller;
import cl.duoc.test.api.Dtos.*;
import cl.duoc.test.model.*;
import cl.duoc.test.repository.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/logbooks")
public class LogbookController {
 private final LogbookRepository logbooks; private final CourseRepository courses; private final SubjectRepository subjects; private final UserRepository users;
 public LogbookController(LogbookRepository l,CourseRepository c,SubjectRepository s,UserRepository u){logbooks=l;courses=c;subjects=s;users=u;}
 @GetMapping public List<LogbookResponse> list(@RequestParam(required=false) Long courseId,@RequestParam(required=false) Long subjectId){List<Logbook> rows=courseId!=null&&subjectId!=null?logbooks.findByCourseIdAndSubjectId(courseId,subjectId):logbooks.findAll();return rows.stream().map(LogbookResponse::from).toList();}
 @GetMapping("/{id}") public LogbookResponse get(@PathVariable Long id){return LogbookResponse.from(logbooks.findById(id).orElseThrow(()->new IllegalArgumentException("BitÃ¡cora no encontrada")));}
 @PostMapping @PreAuthorize("hasRole('PROFESOR')") public LogbookResponse create(@Valid @RequestBody LogbookRequest r,@AuthenticationPrincipal String teacherId){AppUser t=users.findById(teacherId).orElseThrow();Course c=courses.findById(r.courseId()).orElseThrow(()->new IllegalArgumentException("Curso no encontrado"));Subject s=subjects.findById(r.subjectId()).orElseThrow(()->new IllegalArgumentException("Asignatura no encontrada"));return LogbookResponse.from(logbooks.save(new Logbook(c,s,t,r.month(),r.title(),r.purpose(),r.instructions(),r.guidingQuestions())));}
 @PatchMapping("/{id}/status") @PreAuthorize("hasRole('PROFESOR')") public LogbookResponse status(@PathVariable Long id,@RequestParam boolean closed,@AuthenticationPrincipal String teacherId){Logbook l=logbooks.findById(id).orElseThrow(()->new IllegalArgumentException("BitÃ¡cora no encontrada"));if(!l.getTeacher().getId().equals(teacherId))throw new IllegalArgumentException("No puedes modificar esta bitÃ¡cora");l.setClosed(closed);return LogbookResponse.from(logbooks.save(l));}
}
