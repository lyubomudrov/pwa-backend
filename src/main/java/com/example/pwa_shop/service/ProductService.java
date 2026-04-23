package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.CreateProductRequestDto;
import com.example.pwa_shop.dto.ProductResponseDto;
import com.example.pwa_shop.model.entity.Category;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.repository.CategoryRepository;
import com.example.pwa_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDto create(CreateProductRequestDto request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .stockQuantity(request.stockQuantity())
                .available(request.available() != null ? request.available() : true)
                .imageUrl(request.imageUrl())
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);
        return toDto(savedProduct);
    }

    public ProductResponseDto update(Long id, CreateProductRequestDto request) {
        Product product = getEntityById(id);

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setAvailable(request.available() != null ? request.available() : true);
        product.setImageUrl(request.imageUrl());
        product.setCategory(category);

        Product updatedProduct = productRepository.save(product);
        return toDto(updatedProduct);
    }

    public void delete(Long id) {
        Product product = getEntityById(id);
        productRepository.delete(product);
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
        return toDto(getEntityById(id));
    }

    public Product getEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
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