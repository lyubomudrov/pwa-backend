package com.example.pwa_shop.dto.auth;

public record AuthResponse(
        String token,
        Long userId,
        String email,
        String firstName,
        String lastName,
        String role
) {}