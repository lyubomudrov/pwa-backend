package com.example.pwa_shop.dto;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long id,
        Long userId,
        String userEmail,
        Long productId,
        Integer rating,
        String comment,
        LocalDateTime reviewDate
) {
}