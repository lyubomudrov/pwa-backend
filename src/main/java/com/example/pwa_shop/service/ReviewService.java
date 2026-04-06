package com.example.pwa_shop.service;

import com.example.pwa_shop.dto.CreateReviewRequestDto;
import com.example.pwa_shop.dto.ReviewResponseDto;
import com.example.pwa_shop.mapper.EntityDtoMapper;
import com.example.pwa_shop.model.entity.Product;
import com.example.pwa_shop.model.entity.Review;
import com.example.pwa_shop.model.entity.User;
import com.example.pwa_shop.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final EntityDtoMapper mapper;
    private final UserService userService;
    private final ProductService productService;

    public ReviewResponseDto create(CreateReviewRequestDto request) {
        if (request.rating() == null || request.rating() < 1 || request.rating() > 5) {
            throw new RuntimeException("Rating must be between 1 and 5");
        }

        User user = userService.getById(request.userId());
        Product product = productService.getEntityById(request.productId());

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.rating())
                .comment(request.comment())
                .reviewDate(LocalDateTime.now())
                .build();

        return mapper.toReviewDto(reviewRepository.save(review));
    }

    public List<ReviewResponseDto> getByProduct(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream()
                .map(mapper::toReviewDto)
                .toList();
    }
}