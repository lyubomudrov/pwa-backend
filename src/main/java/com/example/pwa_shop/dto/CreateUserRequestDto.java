package com.example.pwa_shop.dto;

public record CreateUserRequestDto(
        String email,
        String password,
        String firstName,
        String lastName
) {
}