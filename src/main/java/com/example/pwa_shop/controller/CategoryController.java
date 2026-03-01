package com.example.pwa_shop.controller;

import com.example.pwa_shop.model.entity.Category;
import com.example.pwa_shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }



    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.create(category);
    }
}

