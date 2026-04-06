package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.ReviewResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Review;
import com.example.pwa_shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EntityDtoMapper mapper;

    public ReviewResponseDto create(Review review) {
        return mapper.toReviewDto(reviewRepository.save(review));
    }

    public List<ReviewResponseDto> getByProduct(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(mapper::toReviewDto)
                .toList();
    }
}