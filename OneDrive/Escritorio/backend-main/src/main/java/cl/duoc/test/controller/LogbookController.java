package cl.duoc.test.controller;

import cl.duoc.test.api.Dtos.LogbookRequest;
import cl.duoc.test.api.Dtos.LogbookResponse;
import cl.duoc.test.service.LogbookService;
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
@RequestMapping("/api/logbooks")
public class LogbookController {

    private final LogbookService logbookService;

    public LogbookController(LogbookService logbookService) {
        this.logbookService = logbookService;
    }

    @GetMapping
    public List<LogbookResponse> list(
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) Long subjectId
    ) {
        return logbookService.list(courseId, subjectId);
    }

    @GetMapping("/{id}")
    public LogbookResponse get(@PathVariable Long id) {
        return logbookService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('PROFESOR')")
    public LogbookResponse create(
            @Valid @RequestBody LogbookRequest r,
            @AuthenticationPrincipal String teacherId
    ) {
        return logbookService.create(r, teacherId);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('PROFESOR')")
    public LogbookResponse status(
            @PathVariable Long id,
            @RequestParam boolean closed,
            @AuthenticationPrincipal String teacherId
    ) {
        return logbookService.updateStatus(id, closed, teacherId);
    }
}
