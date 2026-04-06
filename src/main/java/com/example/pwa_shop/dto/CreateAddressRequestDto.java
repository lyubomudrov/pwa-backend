package com.example.pwa_shop.dto;

public record CreateAddressRequestDto(
        Long userId,
        String city,
        String street,
        String houseNumber,
        String postalCode
) {
}