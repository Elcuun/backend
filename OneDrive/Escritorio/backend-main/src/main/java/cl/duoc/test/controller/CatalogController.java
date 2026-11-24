package cl.duoc.test.controller;

import cl.duoc.test.api.Dtos.NamedResponse;
import cl.duoc.test.service.CatalogService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/courses")
    public List<NamedResponse> courses() {
        return catalogService.findAllCourses();
    }

    @GetMapping("/subjects")
    public List<NamedResponse> subjects() {
        return catalogService.findAllSubjects();
    }
}
