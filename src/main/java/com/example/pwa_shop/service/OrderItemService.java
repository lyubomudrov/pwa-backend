package com.example.pwa_shop.service;

import com.example.pwa_shop.model.entity.OrderItem;
import com.example.pwa_shop.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public List<OrderItem> getByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}