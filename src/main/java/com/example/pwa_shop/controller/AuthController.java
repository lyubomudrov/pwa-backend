package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.auth.AuthResponse;
import com.example.pwa_shop.dto.auth.LoginRequest;
import com.example.pwa_shop.dto.auth.RegisterRequest;
import com.example.pwa_shop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}