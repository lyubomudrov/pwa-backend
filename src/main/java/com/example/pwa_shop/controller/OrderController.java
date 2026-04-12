package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.CreateOrderRequestDto;
import com.example.pwa_shop.dto.OrderResponseDto;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.service.OrderService;
import com.example.pwa_shop.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public OrderResponseDto createOrder(@Valid @RequestBody CreateOrderRequestDto request) {
        User currentUser = userService.getCurrentUser();
        return orderService.createOrder(currentUser, request.addressId());
    }

    @GetMapping("/my")
    public List<OrderResponseDto> getMyOrders() {
        User currentUser = userService.getCurrentUser();
        return orderService.getOrdersByUser(currentUser);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@PathVariable Long orderId) {
        User currentUser = userService.getCurrentUser();
        return orderService.getByIdForUser(orderId, currentUser);
    }
}