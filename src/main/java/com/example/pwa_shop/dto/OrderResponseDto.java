package com.example.pwa_shop.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        LocalDateTime orderDate,
        BigDecimal totalAmount,
        String status,
        AddressResponseDto address,
        List<OrderItemResponseDto> items
) {
}