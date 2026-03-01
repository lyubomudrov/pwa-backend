package com.example.pwa_shop.service;

import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(Product product) {
        if (product.getAvailable() == null) {
            product.setAvailable(true);
        }
        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public List<Product> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }
}
