package cl.duoc.test.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "logbooks")
public class Logbook {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "course_id") private Course course;
    @ManyToOne(optional = false) @JoinColumn(name = "subject_id") private Subject subject;
    @ManyToOne(optional = false) @JoinColumn(name = "teacher_id") private AppUser teacher;
    @Column(nullable = false) private String month;
    @Column(nullable = false) private String title;
    @Column(nullable = false, length = 3000) private String purpose;
    @Column(nullable = false, length = 5000) private String instructions;
    @Column(length = 3000) private String guidingQuestions;
    @Column(nullable = false) private boolean closed = false;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected Logbook() {}
    public Logbook(Course course, Subject subject, AppUser teacher, String month, String title, String purpose, String instructions, String guidingQuestions) {
        this.course = course; this.subject = subject; this.teacher = teacher; this.month = month; this.title = title;
        this.purpose = purpose; this.instructions = instructions; this.guidingQuestions = guidingQuestions;
    }
    public Long getId() { return id; } public Course getCourse() { return course; } public Subject getSubject() { return subject; }
    public AppUser getTeacher() { return teacher; } public String getMonth() { return month; } public String getTitle() { return title; }
    public String getPurpose() { return purpose; } public String getInstructions() { return instructions; }
    public String getGuidingQuestions() { return guidingQuestions; } public boolean isClosed() { return closed; }
    public Instant getCreatedAt() { return createdAt; } public void setClosed(boolean closed) { this.closed = closed; }
}
