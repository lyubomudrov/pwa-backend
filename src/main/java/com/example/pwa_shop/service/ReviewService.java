package com.example.pwa_shop.service;

import com.example.pwa_shop.model.entity.Review;
import com.example.pwa_shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Review create(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }
}
