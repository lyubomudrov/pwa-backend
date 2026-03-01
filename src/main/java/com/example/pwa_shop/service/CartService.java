package com.example.pwa_shop.service;

import com.example.pwa_shop.model.entity.Cart;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    public Cart getByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }
    public Optional<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}

