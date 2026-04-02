package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.ProductResponseDto;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @GetMapping("/category/{categoryId}")
    public List<ProductResponseDto> getByCategory(@PathVariable Long categoryId) {
        return productService.getByCategory(categoryId);
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody Product product) {
        return productService.create(product);
    }
}