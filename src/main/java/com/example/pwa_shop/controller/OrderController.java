package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.CreateOrderRequestDto;
import com.example.pwa_shop.dto.OrderResponseDto;
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
    public OrderResponseDto createOrder(@RequestBody CreateOrderRequestDto request) {
        return orderService.createOrder(request.userId(), request.addressId());
    }

    @GetMapping("/user/{userId}")
    public List<OrderResponseDto> getOrdersByUser(@PathVariable Long userId) {
        return orderService.getOrdersByUser(userId);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@PathVariable Long orderId) {
        return orderService.getById(orderId);
    }
}