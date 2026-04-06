package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.OrderResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.*;
import com.example.pwa_shop.model.enums.OrderStatus;
import com.example.pwa_shop.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EntityDtoMapper mapper;

    @Transactional
    public Order placeOrder(User user, Address address) {

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.NEW);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPurchasePrice(cartItem.getProduct().getPrice());

            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(
                    orderItem.getPurchasePrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }

    @Transactional
    public OrderResponseDto createOrder(Long userId, Long addressId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        Order savedOrder = placeOrder(user, address);
        return mapper.toOrderDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(mapper::toOrderDto)
                .toList();
    }

    public OrderResponseDto getById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapper.toOrderDto(order);
    }
}