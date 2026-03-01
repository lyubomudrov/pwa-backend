package com.example.pwa_shop.repository;

import com.example.pwa_shop.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
