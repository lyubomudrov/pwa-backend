package com.example.pwa_shop.mapper;

import com.example.pwa_shop.dto.*;
import com.example.pwa_shop.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EntityDtoMapper {

    public UserResponseDto toUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public CategoryResponseDto toCategoryDto(Category category) {
        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public AddressResponseDto toAddressDto(Address address) {
        return new AddressResponseDto(
                address.getId(),
                address.getCity(),
                address.getStreet(),
                address.getHouseNumber(),
                address.getPostalCode()
        );
    }

    public CartItemResponseDto toCartItemDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getQuantity(),
                cartItem.getProduct() != null ? cartItem.getProduct().getId() : null,
                cartItem.getProduct() != null ? cartItem.getProduct().getName() : null,
                cartItem.getProduct() != null ? cartItem.getProduct().getPrice() : null,
                cartItem.getProduct() != null ? cartItem.getProduct().getImageUrl() : null
        );
    }

    public OrderItemResponseDto toOrderItemDto(OrderItem orderItem) {
        return new OrderItemResponseDto(
                orderItem.getId(),
                orderItem.getProduct() != null ? orderItem.getProduct().getId() : null,
                orderItem.getProduct() != null ? orderItem.getProduct().getName() : null,
                orderItem.getQuantity(),
                orderItem.getPurchasePrice()
        );
    }

    public OrderResponseDto toOrderDto(Order order) {
        List<OrderItemResponseDto> items = order.getItems()
                .stream()
                .map(this::toOrderItemDto)
                .toList();

        return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus() != null ? order.getStatus().name() : null,
                order.getAddress() != null ? toAddressDto(order.getAddress()) : null,
                items
        );
    }

    public ReviewResponseDto toReviewDto(Review review) {
        return new ReviewResponseDto(
                review.getId(),
                review.getUser() != null ? review.getUser().getId() : null,
                review.getUser() != null ? review.getUser().getEmail() : null,
                review.getProduct() != null ? review.getProduct().getId() : null,
                review.getRating(),
                review.getComment(),
                review.getReviewDate()
        );
    }
}