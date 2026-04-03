package com.example.pwa_shop.dto;

import java.math.BigDecimal;

public record CartItemResponseDto(
        Long id,
        Integer quantity,
        Long productId,
        String productName,
        BigDecimal productPrice,
        String imageUrl
) {
}