package com.example.pwa_shop.dto;

public record CreateOrderRequestDto(
        Long userId,
        Long addressId
) {
}