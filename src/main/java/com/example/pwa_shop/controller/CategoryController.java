package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.CategoryResponseDto;
import com.example.pwa_shop.dto.CreateCategoryRequestDto;
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
    public List<CategoryResponseDto> getAll() {
        return categoryService.getAll();
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CreateCategoryRequestDto request) {
        return categoryService.create(request);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(@PathVariable Long id, @RequestBody CreateCategoryRequestDto request) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}