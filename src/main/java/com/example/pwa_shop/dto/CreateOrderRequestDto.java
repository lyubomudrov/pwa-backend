package com.example.pwa_shop.dto;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequestDto(
        @NotNull Long addressId
) {
}