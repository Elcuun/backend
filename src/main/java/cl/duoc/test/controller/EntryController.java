package cl.duoc.test.controller;
import cl.duoc.test.api.Dtos.*;
import cl.duoc.test.model.*;
import cl.duoc.test.repository.*;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api")
public class EntryController {
 private final EntryRepository entries; private final LogbookRepository logbooks; private final UserRepository users;
 public EntryController(EntryRepository e,LogbookRepository l,UserRepository u){entries=e;logbooks=l;users=u;}
 @GetMapping("/logbooks/{id}/entries") @PreAuthorize("hasRole('PROFESOR')") public List<EntryResponse> byLogbook(@PathVariable Long id,@AuthenticationPrincipal String uid){Logbook l=logbooks.findById(id).orElseThrow(()->new IllegalArgumentException("BitÃ¡cora no encontrada"));if(!l.getTeacher().getId().equals(uid))throw new IllegalArgumentException("No puedes ver esta bitÃ¡cora");return entries.findByLogbookId(id).stream().map(EntryResponse::from).toList();}
 @PostMapping("/logbooks/{id}/entries") @PreAuthorize("hasRole('ALUMNO')") public EntryResponse create(@PathVariable Long id,@Valid @RequestBody EntryRequest r,@AuthenticationPrincipal String studentId){Logbook l=logbooks.findById(id).orElseThrow(()->new IllegalArgumentException("BitÃ¡cora no encontrada"));if(l.isClosed())throw new IllegalArgumentException("La bitÃ¡cora estÃ¡ cerrada");AppUser s=users.findById(studentId).orElseThrow();if(s.getCourse()!=null&&!s.getCourse().getId().equals(l.getCourse().getId()))throw new IllegalArgumentException("La bitÃ¡cora no corresponde a tu curso");return EntryResponse.from(entries.save(new LogbookEntry(l,s,s.getFirstName()+" "+s.getLastName(),r.contents(),r.activities(),r.learnings(),r.doubts(),r.photoUri(),r.latitude(),r.longitude())));}
 @PatchMapping("/entries/{id}/review") @PreAuthorize("hasRole('PROFESOR')") public EntryResponse review(@PathVariable Long id,@RequestBody ReviewRequest r,@AuthenticationPrincipal String teacherId){LogbookEntry e=entries.findById(id).orElseThrow(()->new IllegalArgumentException("Entrega no encontrada"));if(!e.getLogbook().getTeacher().getId().equals(teacherId))throw new IllegalArgumentException("No puedes revisar esta entrega");e.review(r.feedback(),r.isReviewed());return EntryResponse.from(entries.save(e));}
 @GetMapping("/guardian/entries") @PreAuthorize("hasRole('APODERADO')") public List<EntryResponse> guardianSearch(@RequestParam(required=false) String name,@RequestParam(required=false) String email){if(email!=null&&!email.isBlank()){AppUser u=users.findByEmailIgnoreCase(email.trim()).orElseThrow(()->new IllegalArgumentException("Estudiante no encontrado"));if(u.getRole()!=Role.ALUMNO)throw new IllegalArgumentException("Ese correo no corresponde a un estudiante");return entries.findByStudentId(u.getId()).stream().map(EntryResponse::from).toList();}if(name==null||name.isBlank())throw new IllegalArgumentException("Ingresa nombre o correo del estudiante");return entries.findByStudentNameContainingIgnoreCase(name.trim()).stream().map(EntryResponse::from).toList();}
}
