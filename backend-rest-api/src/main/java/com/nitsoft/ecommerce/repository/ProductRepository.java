/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author VS9 X64Bit
 */
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p, ProductCategory pc WHERE pc.categoryId = :categoryId AND pc.productId = p.productId")
    Iterable<Product> findAllByCategoryId(@Param("categoryId") Long categoryId);
}
