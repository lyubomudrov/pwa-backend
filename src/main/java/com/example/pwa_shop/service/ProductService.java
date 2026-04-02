package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.ProductResponseDto;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto create(Product product) {
        if (product.getAvailable() == null) {
            product.setAvailable(true);
        }

        Product savedProduct = productRepository.save(product);
        return toDto(savedProduct);
    }

    public List<ProductResponseDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    public List<ProductResponseDto> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    public ProductResponseDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return toDto(product);
    }

    private ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getAvailable(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}