package com.digitaltol.translation.controller;


import com.digitaltol.translation.dto.AuthRequest;
import com.digitaltol.translation.dto.AuthResponse;
import com.digitaltol.translation.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        var token = authService.authenticate(req.username(), req.password());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}