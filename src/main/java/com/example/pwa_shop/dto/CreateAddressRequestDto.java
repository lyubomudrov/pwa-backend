package com.example.pwa_shop.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateAddressRequestDto(
        @NotBlank String city,
        @NotBlank String street,
        @NotBlank String houseNumber,
        @NotBlank String postalCode
) {
}