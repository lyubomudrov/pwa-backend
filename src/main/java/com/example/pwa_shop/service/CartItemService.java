package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.AddToCartRequestDto;
import com.example.pwa_shop.dto.CartItemResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Cart;
import com.example.pwa_shop.model.entity.CartItem;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final EntityDtoMapper mapper;
    private final ProductService productService;
    private final CartService cartService;

    public CartItemResponseDto addToCart(AddToCartRequestDto request, User user) {
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        Product product = productService.getEntityById(request.productId());

        if (Boolean.FALSE.equals(product.getAvailable())) {
            throw new RuntimeException("Product is not available");
        }

        if (product.getStockQuantity() == null || product.getStockQuantity() <= 0) {
            throw new RuntimeException("Product is out of stock");
        }

        Cart cart = cartService.getOrCreateCart(user);

        CartItem savedItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .map(item -> {
                    int newQuantity = item.getQuantity() + request.quantity();

                    if (newQuantity > product.getStockQuantity()) {
                        throw new RuntimeException("Not enough product in stock");
                    }

                    item.setQuantity(newQuantity);
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    if (request.quantity() > product.getStockQuantity()) {
                        throw new RuntimeException("Not enough product in stock");
                    }

                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setProduct(product);
                    item.setQuantity(request.quantity());
                    return cartItemRepository.save(item);
                });

        return mapper.toCartItemDto(savedItem);
    }

    public List<CartItemResponseDto> getByUser(User user) {
        Cart cart = cartService.getOrCreateCart(user);

        return cartItemRepository.findByCartId(cart.getId())
                .stream()
                .map(mapper::toCartItemDto)
                .toList();
    }

    public void remove(Long id, User user) {
        CartItem cartItem = cartItemRepository.findByIdAndCartUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
    }
}