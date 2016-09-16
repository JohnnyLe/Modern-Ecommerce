package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends PagingAndSortingRepository<Review, Integer> {

    @Query("SELECT r FROM Review r WHERE r.productId = :productId")
    Page<Review> findByProductId(@Param("productId") long productId, Pageable pageable);

}
