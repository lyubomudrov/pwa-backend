package com.example.pwa_shop.dto;

public record CreateReviewRequestDto(
        Long userId,
        Long productId,
        Integer rating,
        String comment
) {
}