package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.AddressResponseDto;
import com.example.pwa_shop.dto.CreateAddressRequestDto;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.service.AddressService;
import com.example.pwa_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @PostMapping
    public AddressResponseDto createAddress(@Valid @RequestBody CreateAddressRequestDto request) {
        User currentUser = userService.getCurrentUser();
        return addressService.create(request, currentUser);
    }

    @GetMapping("/my")
    public List<AddressResponseDto> getMyAddresses() {
        User currentUser = userService.getCurrentUser();
        return addressService.getCurrentUserAddresses(currentUser);
    }

    @DeleteMapping("/{addressId}")
    public void delete(@PathVariable Long addressId) {
        User currentUser = userService.getCurrentUser();
        addressService.deleteForUser(addressId, currentUser);
    }
}