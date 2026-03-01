package com.example.pwa_shop.controller;

import com.example.pwa_shop.model.entity.Order;
import com.example.pwa_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(
            @RequestParam Long userId,
            @RequestParam Long addressId
    ) {
        return orderService.createOrder(userId, addressId);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable Long orderId) {
        return orderService.getById(orderId);
    }
}

