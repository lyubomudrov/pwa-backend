package com.example.pwa_shop.dto;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long id,
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal purchasePrice
) {
}