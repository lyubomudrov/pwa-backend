package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.AddToCartRequestDto;
import com.example.pwa_shop.dto.CartItemResponseDto;
import com.example.pwa_shop.service.CartItemService;
import com.example.pwa_shop.service.CartService;
import com.example.pwa_shop.service.ProductService;
import com.example.pwa_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/{userId}")
    public List<CartItemResponseDto> getCart(@PathVariable Long userId) {
        return cartService.findByUserId(userId)
                .map(cart -> cartItemService.getByCartId(cart.getId()))
                .orElse(List.of());
    }

    @PostMapping("/add")
    public CartItemResponseDto addToCart(@RequestBody AddToCartRequestDto request) {
        return cartItemService.addToCart(request);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartItemService.remove(cartItemId);
    }
}