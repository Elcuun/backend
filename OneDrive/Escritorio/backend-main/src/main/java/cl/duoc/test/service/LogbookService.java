package cl.duoc.test.service;

import cl.duoc.test.api.Dtos.LogbookRequest;
import cl.duoc.test.api.Dtos.LogbookResponse;
import cl.duoc.test.model.AppUser;
import cl.duoc.test.model.Course;
import cl.duoc.test.model.Logbook;
import cl.duoc.test.model.Subject;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.LogbookRepository;
import cl.duoc.test.repository.SubjectRepository;
import cl.duoc.test.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogbookService {

    private final LogbookRepository logbooks;
    private final CourseRepository courses;
    private final SubjectRepository subjects;
    private final UserRepository users;

    public LogbookService(LogbookRepository logbooks, CourseRepository courses, SubjectRepository subjects, UserRepository users) {
        this.logbooks = logbooks;
        this.courses = courses;
        this.subjects = subjects;
        this.users = users;
    }

    @Transactional(readOnly = true)
    public List<LogbookResponse> list(Long courseId, Long subjectId) {
        List<Logbook> rows = (courseId != null && subjectId != null)
                ? logbooks.findByCourseIdAndSubjectId(courseId, subjectId)
                : logbooks.findAll();
        return rows.stream().map(LogbookResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public LogbookResponse getById(Long id) {
        return LogbookResponse.from(findLogbookOrThrow(id));
    }

    @Transactional
    public LogbookResponse create(LogbookRequest r, String teacherId) {
        AppUser teacher = users.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Docente no encontrado"));
        Course course = courses.findById(r.courseId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Subject subject = subjects.findById(r.subjectId())
                .orElseThrow(() -> new IllegalArgumentException("Asignatura no encontrada"));
        Logbook saved = logbooks.save(new Logbook(
                course,
                subject,
                teacher,
                r.month(),
                r.title(),
                r.purpose(),
                r.instructions(),
                r.guidingQuestions()
        ));
        return LogbookResponse.from(saved);
    }

    @Transactional
    public LogbookResponse updateStatus(Long id, boolean closed, String teacherId) {
        Logbook logbook = findLogbookOrThrow(id);
        if (!logbook.getTeacher().getId().equals(teacherId)) {
            throw new IllegalArgumentException("No puedes modificar esta bitácora");
        }
        logbook.setClosed(closed);
        return LogbookResponse.from(logbooks.save(logbook));
    }

    private Logbook findLogbookOrThrow(Long id) {
        return logbooks.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bitácora no encontrada"));
    }
}
