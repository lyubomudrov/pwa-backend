package com.example.pwa_shop.dto;

public record AddToCartRequestDto(
        Long userId,
        Long productId,
        Integer quantity
) {
}