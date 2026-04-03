package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.CartItemResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Cart;
import com.example.pwa_shop.model.entity.CartItem;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final EntityDtoMapper mapper;

    public CartItemResponseDto addProductToCart(Cart cart, Product product, int quantity) {

        if (quantity <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        CartItem savedItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .map(item -> {
                    item.setQuantity(item.getQuantity() + quantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setProduct(product);
                    item.setQuantity(quantity);
                    return cartItemRepository.save(item);
                });

        return mapper.toCartItemDto(savedItem);
    }

    public List<CartItemResponseDto> getByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId)
                .stream()
                .map(mapper::toCartItemDto)
                .toList();
    }

    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }
}