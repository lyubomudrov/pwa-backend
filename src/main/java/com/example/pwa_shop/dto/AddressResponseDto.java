package com.example.pwa_shop.dto;

public record AddressResponseDto(
        Long id,
        String city,
        String street,
        String houseNumber,
        String postalCode
) {
}