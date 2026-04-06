package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.CategoryResponseDto;
import com.example.pwa_shop.dto.CreateCategoryRequestDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Category;
import com.example.pwa_shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final EntityDtoMapper mapper;

    public CategoryResponseDto create(CreateCategoryRequestDto request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        return mapper.toCategoryDto(categoryRepository.save(category));
    }

    public List<CategoryResponseDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(mapper::toCategoryDto)
                .toList();
    }
}