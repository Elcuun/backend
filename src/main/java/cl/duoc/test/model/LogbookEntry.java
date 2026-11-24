package cl.duoc.test.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name = "logbook_entries")
public class LogbookEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional = false) @JoinColumn(name = "logbook_id") private Logbook logbook;
    @ManyToOne @JoinColumn(name = "student_id") private AppUser student;
    @Column(nullable = false) private String studentName;
    @Column(nullable = false, length = 3000) private String contents;
    @Column(nullable = false, length = 3000) private String activities;
    @Column(nullable = false, length = 3000) private String learnings;
    @Column(length = 3000) private String doubts;
    private String photoUri;
    private Double latitude;
    private Double longitude;
    @Column(length = 3000) private String feedback;
    @Column(nullable = false) private boolean reviewed = false;
    @Column(nullable = false, updatable = false) private Instant createdAt = Instant.now();
    protected LogbookEntry() {}
    public LogbookEntry(Logbook logbook, AppUser student, String studentName, String contents, String activities, String learnings,
                        String doubts, String photoUri, Double latitude, Double longitude) {
        this.logbook = logbook; this.student = student; this.studentName = studentName; this.contents = contents;
        this.activities = activities; this.learnings = learnings; this.doubts = doubts; this.photoUri = photoUri;
        this.latitude = latitude; this.longitude = longitude;
    }
    public Long getId() { return id; } public Logbook getLogbook() { return logbook; } public AppUser getStudent() { return student; }
    public String getStudentName() { return studentName; } public String getContents() { return contents; }
    public String getActivities() { return activities; } public String getLearnings() { return learnings; }
    public String getDoubts() { return doubts; } public String getPhotoUri() { return photoUri; }
    public Double getLatitude() { return latitude; } public Double getLongitude() { return longitude; }
    public String getFeedback() { return feedback; } public boolean isReviewed() { return reviewed; } public Instant getCreatedAt() { return createdAt; }
    public void review(String feedback, boolean reviewed) { this.feedback = feedback; this.reviewed = reviewed; }
}
