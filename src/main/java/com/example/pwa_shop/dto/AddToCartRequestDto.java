package com.example.pwa_shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AddToCartRequestDto(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantity
) {
}