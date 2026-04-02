package com.example.pwa_shop.repository;

import com.example.pwa_shop.model.entity.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @EntityGraph(attributePaths = {"category"})
    List<Product> findAll();

    @EntityGraph(attributePaths = {"category"})
    List<Product> findByCategoryId(Long categoryId);

    @EntityGraph(attributePaths = {"category"})
    Optional<Product> findById(Long id);
}