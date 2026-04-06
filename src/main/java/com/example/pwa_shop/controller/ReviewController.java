package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.ReviewResponseDto;
import com.example.pwa_shop.model.entity.Review;
import com.example.pwa_shop.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ReviewResponseDto create(@RequestBody Review review) {
        return reviewService.create(review);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponseDto> getByProduct(@PathVariable Long productId) {
        return reviewService.getByProduct(productId);
    }
}