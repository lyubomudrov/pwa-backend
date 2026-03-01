package com.example.pwa_shop.controller;

import com.example.pwa_shop.model.entity.Address;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.service.AddressService;
import com.example.pwa_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public Address createAddress(
            @RequestParam Long userId,
            @RequestBody Address address
    ) {
        return addressService.create(userId, address);
    }

    @GetMapping("/user/{userId}")
    public List<Address> getByUser(@PathVariable Long userId) {
        return addressService.getByUserId(userId);
    }

    @DeleteMapping("/{addressId}")
    public void delete(
            @PathVariable Long addressId,
            @RequestParam Long userId
    ) {
        addressService.delete(addressId, userId);
    }
}
