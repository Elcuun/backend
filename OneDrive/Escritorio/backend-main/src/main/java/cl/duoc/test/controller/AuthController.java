package cl.duoc.test.controller;

import cl.duoc.test.api.Dtos.AuthResponse;
import cl.duoc.test.api.Dtos.LoginRequest;
import cl.duoc.test.api.Dtos.UserRequest;
import cl.duoc.test.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody UserRequest r) {
        return authService.register(r);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest r) {
        return authService.login(r);
    }
}
