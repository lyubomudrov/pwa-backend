package com.example.pwa_shop.service;

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

    @Transactional
    public Order placeOrder(User user, Address address) {

        // Корзина пользователя
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Элементы корзины
        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        // Создаём заказ (НО НЕ СОХРАНЯЕМ)
        Order order = new Order();
        order.setUser(user);
        order.setAddress(address);
        order.setStatus(OrderStatus.NEW);
        order.setOrderDate(LocalDateTime.now());

        BigDecimal totalAmount = BigDecimal.ZERO;

        //  Создание OrderItem
        for (CartItem cartItem : cartItems) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPurchasePrice(cartItem.getProduct().getPrice());

            order.getItems().add(orderItem); // 🔥 ВАЖНО

            totalAmount = totalAmount.add(
                    orderItem.getPurchasePrice()
                            .multiply(BigDecimal.valueOf(orderItem.getQuantity()))
            );
        }

        // Итоговая сумма
        order.setTotalAmount(totalAmount);

        // Сохраняем заказ ОДИН РАЗ
        Order savedOrder = orderRepository.save(order);

        // Очищаем корзину
        cartItemRepository.deleteAll(cartItems);

        return savedOrder;
    }
    @Transactional
    public Order createOrder(Long userId, Long addressId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException("Address does not belong to user");
        }

        return placeOrder(user, address);
    }
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
    public Order getById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}

