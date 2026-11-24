package cl.duoc.test.controller;

import cl.duoc.test.api.Dtos.EntryRequest;
import cl.duoc.test.api.Dtos.EntryResponse;
import cl.duoc.test.api.Dtos.ReviewRequest;
import cl.duoc.test.service.EntryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping("/logbooks/{id}/entries")
    @PreAuthorize("hasRole('PROFESOR')")
    public List<EntryResponse> byLogbook(
            @PathVariable Long id,
            @AuthenticationPrincipal String teacherId
    ) {
        return entryService.listByLogbook(id, teacherId);
    }

    @PostMapping("/logbooks/{id}/entries")
    @PreAuthorize("hasRole('ALUMNO')")
    public EntryResponse create(
            @PathVariable Long id,
            @Valid @RequestBody EntryRequest r,
            @AuthenticationPrincipal String studentId
    ) {
        return entryService.create(id, r, studentId);
    }

    @PatchMapping("/entries/{id}/review")
    @PreAuthorize("hasRole('PROFESOR')")
    public EntryResponse review(
            @PathVariable Long id,
            @RequestBody ReviewRequest r,
            @AuthenticationPrincipal String teacherId
    ) {
        return entryService.review(id, r, teacherId);
    }

    @GetMapping("/guardian/entries")
    @PreAuthorize("hasRole('APODERADO')")
    public List<EntryResponse> guardianSearch(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email
    ) {
        return entryService.guardianSearch(name, email);
    }
}
