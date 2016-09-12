package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    @Query("SELECT p FROM Product p, ProductCategory pc WHERE pc.categoryId = :categoryId AND pc.productId = p.productId")
    Iterable<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);

}
