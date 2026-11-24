package cl.duoc.test.service;

import cl.duoc.test.api.Dtos.EntryRequest;
import cl.duoc.test.api.Dtos.EntryResponse;
import cl.duoc.test.api.Dtos.ReviewRequest;
import cl.duoc.test.model.AppUser;
import cl.duoc.test.model.Logbook;
import cl.duoc.test.model.LogbookEntry;
import cl.duoc.test.model.Role;
import cl.duoc.test.repository.EntryRepository;
import cl.duoc.test.repository.LogbookRepository;
import cl.duoc.test.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntryService {

    private final EntryRepository entries;
    private final LogbookRepository logbooks;
    private final UserRepository users;

    public EntryService(EntryRepository entries, LogbookRepository logbooks, UserRepository users) {
        this.entries = entries;
        this.logbooks = logbooks;
        this.users = users;
    }

    @Transactional(readOnly = true)
    public List<EntryResponse> listByLogbook(Long logbookId, String teacherId) {
        Logbook logbook = logbooks.findById(logbookId)
                .orElseThrow(() -> new IllegalArgumentException("Bitácora no encontrada"));
        if (!logbook.getTeacher().getId().equals(teacherId)) {
            throw new IllegalArgumentException("No puedes ver esta bitácora");
        }
        return entries.findByLogbookId(logbookId).stream().map(EntryResponse::from).toList();
    }

    @Transactional
    public EntryResponse create(Long logbookId, EntryRequest r, String studentId) {
        Logbook logbook = logbooks.findById(logbookId)
                .orElseThrow(() -> new IllegalArgumentException("Bitácora no encontrada"));
        if (logbook.isClosed()) {
            throw new IllegalArgumentException("La bitácora está cerrada");
        }
        AppUser student = users.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        if (student.getCourse() != null && !student.getCourse().getId().equals(logbook.getCourse().getId())) {
            throw new IllegalArgumentException("La bitácora no corresponde a tu curso");
        }
        String studentName = student.getFirstName() + " " + student.getLastName();
        LogbookEntry saved = entries.save(new LogbookEntry(
                logbook,
                student,
                studentName,
                r.contents(),
                r.activities(),
                r.learnings(),
                r.doubts(),
                r.photoUri(),
                r.latitude(),
                r.longitude()
        ));
        return EntryResponse.from(saved);
    }

    @Transactional
    public EntryResponse review(Long entryId, ReviewRequest r, String teacherId) {
        LogbookEntry entry = entries.findById(entryId)
                .orElseThrow(() -> new IllegalArgumentException("Entrega no encontrada"));
        if (!entry.getLogbook().getTeacher().getId().equals(teacherId)) {
            throw new IllegalArgumentException("No puedes revisar esta entrega");
        }
        entry.review(r.feedback(), r.isReviewed());
        return EntryResponse.from(entries.save(entry));
    }

    @Transactional(readOnly = true)
    public List<EntryResponse> guardianSearch(String name, String email) {
        if (email != null && !email.isBlank()) {
            AppUser student = users.findByEmailIgnoreCase(email.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
            if (student.getRole() != Role.ALUMNO) {
                throw new IllegalArgumentException("Ese correo no corresponde a un estudiante");
            }
            return entries.findByStudentId(student.getId()).stream().map(EntryResponse::from).toList();
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ingresa nombre o correo del estudiante");
        }
        return entries.findByStudentNameContainingIgnoreCase(name.trim()).stream().map(EntryResponse::from).toList();
    }
}
