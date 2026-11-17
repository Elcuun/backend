package cl.duoc.test.api;
import cl.duoc.test.model.*;
import jakarta.validation.constraints.*;
import java.time.Instant;
public final class Dtos {
 private Dtos() {}
 public record UserRequest(@NotBlank @Size(max=80) String firstName,@NotBlank @Size(max=80) String lastName,@NotBlank @Email String email,@NotBlank @Size(min=6,max=100) String password,@NotNull Role role,Long courseId) {}
 public record LoginRequest(@NotBlank @Email String email,@NotBlank String password,@NotNull Role role) {}
 public record UserResponse(String id,String firstName,String lastName,String email,Role role,Long courseId) { public static UserResponse from(AppUser u) { return new UserResponse(u.getId(),u.getFirstName(),u.getLastName(),u.getEmail(),u.getRole(),u.getCourse()==null?null:u.getCourse().getId()); } }
 public record AuthResponse(UserResponse user,String token) {}
 public record NamedResponse(Long id,String name) { public static NamedResponse from(Course c){return new NamedResponse(c.getId(),c.getName());} public static NamedResponse from(Subject s){return new NamedResponse(s.getId(),s.getName());} }
 public record LogbookRequest(@NotNull Long courseId,@NotNull Long subjectId,@NotBlank String month,@NotBlank String title,@NotBlank String purpose,@NotBlank String instructions,String guidingQuestions) {}
 public record LogbookResponse(Long id,Long courseId,Long subjectId,String month,String title,String purpose,String instructions,String guidingQuestions,boolean isClosed,Instant createdAt) { public static LogbookResponse from(Logbook l){return new LogbookResponse(l.getId(),l.getCourse().getId(),l.getSubject().getId(),l.getMonth(),l.getTitle(),l.getPurpose(),l.getInstructions(),l.getGuidingQuestions(),l.isClosed(),l.getCreatedAt());} }
 public record EntryRequest(@NotBlank String studentName,@NotBlank String contents,@NotBlank String activities,@NotBlank String learnings,String doubts,String photoUri,Double latitude,Double longitude) {}
 public record ReviewRequest(String feedback,boolean isReviewed) {}
 public record EntryResponse(Long id,Long logbookId,String studentName,String contents,String activities,String learnings,String doubts,String photoUri,Double latitude,Double longitude,String feedback,boolean isReviewed,Instant createdAt) { public static EntryResponse from(LogbookEntry e){return new EntryResponse(e.getId(),e.getLogbook().getId(),e.getStudentName(),e.getContents(),e.getActivities(),e.getLearnings(),e.getDoubts(),e.getPhotoUri(),e.getLatitude(),e.getLongitude(),e.getFeedback(),e.isReviewed(),e.getCreatedAt());} }
}
