package cl.duoc.test.service;

import cl.duoc.test.api.Dtos.NamedResponse;
import cl.duoc.test.repository.CourseRepository;
import cl.duoc.test.repository.SubjectRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CatalogService {

    private final CourseRepository courses;
    private final SubjectRepository subjects;

    public CatalogService(CourseRepository courses, SubjectRepository subjects) {
        this.courses = courses;
        this.subjects = subjects;
    }

    public List<NamedResponse> findAllCourses() {
        return courses.findAll().stream().map(NamedResponse::from).toList();
    }

    public List<NamedResponse> findAllSubjects() {
        return subjects.findAll().stream().map(NamedResponse::from).toList();
    }
}
