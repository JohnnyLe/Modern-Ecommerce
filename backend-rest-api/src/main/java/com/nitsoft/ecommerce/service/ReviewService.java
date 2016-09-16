package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Review;
import com.nitsoft.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Page<Review> findByProductId(long productId, int pageNumber, int pageSize) {
        return reviewRepository.findByProductId(productId, new PageRequest(pageNumber, pageSize));
    }

    public Review save(Review review) {
        return reviewRepository.save(review);
    }
}
