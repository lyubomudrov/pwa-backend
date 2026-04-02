package com.example.pwa_shop.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Boolean available,
        String imageUrl,
        Long categoryId,
        String categoryName
) {
}