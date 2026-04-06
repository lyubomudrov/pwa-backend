package com.example.pwa_shop.controller;

import com.example.pwa_shop.dto.CreateReviewRequestDto;
import com.example.pwa_shop.dto.ReviewResponseDto;
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
    public ReviewResponseDto create(@RequestBody CreateReviewRequestDto request) {
        return reviewService.create(request);
    }

    @GetMapping("/product/{productId}")
    public List<ReviewResponseDto> getByProduct(@PathVariable Long productId) {
        return reviewService.getByProduct(productId);
    }
}