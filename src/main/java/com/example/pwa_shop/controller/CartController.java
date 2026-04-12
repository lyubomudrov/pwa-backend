package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.AddToCartRequestDto;
import com.example.pwa_shop.dto.CartItemResponseDto;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.service.CartItemService;
import com.example.pwa_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;
    private final UserService userService;

    @GetMapping
    public List<CartItemResponseDto> getCart() {
        User currentUser = userService.getCurrentUser();
        return cartItemService.getByUser(currentUser);
    }

    @PostMapping("/add")
    public CartItemResponseDto addToCart(@Valid @RequestBody AddToCartRequestDto request) {
        User currentUser = userService.getCurrentUser();
        return cartItemService.addToCart(request, currentUser);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        User currentUser = userService.getCurrentUser();
        cartItemService.remove(cartItemId, currentUser);
    }
}