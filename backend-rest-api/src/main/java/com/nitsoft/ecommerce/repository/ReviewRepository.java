package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Review;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends CrudRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.productId = :productId")
    Review findByProductId(@Param("productId") int productId);
    
}
