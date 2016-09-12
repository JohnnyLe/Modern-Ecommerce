package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Review;
import com.nitsoft.ecommerce.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review findByProductId(int productId) {
        return reviewRepository.findByProductId(productId);
    }
    
    public Review save(Review review){
        return reviewRepository.save(review);
    }
}
