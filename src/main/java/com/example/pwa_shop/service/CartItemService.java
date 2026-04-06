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
    private final UserService userService;
    private final ProductService productService;
    private final CartService cartService;

    public CartItemResponseDto addToCart(AddToCartRequestDto request) {
        if (request.quantity() == null || request.quantity() <= 0) {
            throw new RuntimeException("Quantity must be positive");
        }

        User user = userService.getById(request.userId());
        Product product = productService.getEntityById(request.productId());
        Cart cart = cartService.getOrCreateCart(user);

        CartItem savedItem = cartItemRepository
                .findByCartIdAndProductId(cart.getId(), product.getId())
                .map(item -> {
                    item.setQuantity(item.getQuantity() + request.quantity());
                    return cartItemRepository.save(item);
                })
                .orElseGet(() -> {
                    CartItem item = new CartItem();
                    item.setCart(cart);
                    item.setProduct(product);
                    item.setQuantity(request.quantity());
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