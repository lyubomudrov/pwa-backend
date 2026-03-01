package com.example.pwa_shop.controller;

import com.example.pwa_shop.model.entity.*;
import com.example.pwa_shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.example.pwa_shop.service.CartService;
import com.example.pwa_shop.service.CartItemService;
import com.example.pwa_shop.service.UserService;
import com.example.pwa_shop.service.ProductService;

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
    public List<CartItem> getCart(@PathVariable Long userId) {
        return cartService.findByUserId(userId)
                .map(cart -> cartItemService.getByCartId(cart.getId()))
                .orElse(List.of());
    }

    @PostMapping("/add")
    public CartItem addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        User user = userService.getById(userId);
        Product product = productService.getById(productId);

        Cart cart = cartService.getOrCreateCart(user);

        return cartItemService.addProductToCart(cart, product, quantity);
    }

    @DeleteMapping("/item/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartItemService.remove(cartItemId);
    }
}

