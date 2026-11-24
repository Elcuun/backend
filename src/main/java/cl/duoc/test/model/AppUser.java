package cl.duoc.test.model;

import jakarta.persistence.*;
import java.util.UUID;

@Entity @Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class AppUser {
    @Id private String id = UUID.randomUUID().toString();
    @Column(nullable = false) private String firstName;
    @Column(nullable = false) private String lastName;
    @Column(nullable = false) private String email;
    @Column(nullable = false) private String passwordHash;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Role role;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "course_id") private Course course;
    protected AppUser() {}
    public AppUser(String firstName, String lastName, String email, String passwordHash, Role role, Course course) {
        this.firstName = firstName; this.lastName = lastName; this.email = email;
        this.passwordHash = passwordHash; this.role = role; this.course = course;
    }
    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }
    public Course getCourse() { return course; }
}
