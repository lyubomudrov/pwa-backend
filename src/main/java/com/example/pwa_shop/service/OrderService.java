package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.OrderResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Address;
import com.example.pwa_shop.model.entity.Cart;
import com.example.pwa_shop.model.entity.CartItem;
import com.example.pwa_shop.model.entity.Order;
import com.example.pwa_shop.model.entity.OrderItem;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.model.enums.OrderStatus;
import com.example.pwa_shop.repository.AddressRepository;
import com.example.pwa_shop.repository.CartItemRepository;
import com.example.pwa_shop.repository.CartRepository;
import com.example.pwa_shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final EntityDtoMapper mapper;

    @Transactional
    public OrderResponseDto createOrder(User user, Long addressId) {
        Address address = addressRepository.findByIdAndUserId(addressId, user.getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

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
            Product product = cartItem.getProduct();

            if (Boolean.FALSE.equals(product.getAvailable())) {
                throw new RuntimeException("Product is not available: " + product.getName());
            }

            if (product.getStockQuantity() == null || product.getStockQuantity() < cartItem.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());

            if (product.getStockQuantity() == 0) {
                product.setAvailable(false);
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPurchasePrice(product.getPrice());

            order.getItems().add(orderItem);

            totalAmount = totalAmount.add(
                    orderItem.getPurchasePrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);
        cartItemRepository.deleteAll(cartItems);

        return mapper.toOrderDto(savedOrder);
    }

    public List<OrderResponseDto> getOrdersByUser(User user) {
        return orderRepository.findByUserId(user.getId())
                .stream()
                .map(mapper::toOrderDto)
                .toList();
    }

    public OrderResponseDto getByIdForUser(Long orderId, User user) {
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        return mapper.toOrderDto(order);
    }
}