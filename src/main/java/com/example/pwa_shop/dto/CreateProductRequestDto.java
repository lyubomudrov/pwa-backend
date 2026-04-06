package com.example.pwa_shop.dto;

import java.math.BigDecimal;

public record CreateProductRequestDto(
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Boolean available,
        String imageUrl,
        Long categoryId
) {
}