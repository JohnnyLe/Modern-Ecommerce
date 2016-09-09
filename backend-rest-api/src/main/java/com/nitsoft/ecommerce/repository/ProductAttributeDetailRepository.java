/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author VS9 X64Bit
 */
public interface ProductAttributeDetailRepository extends CrudRepository<ProductAttributeDetail, Long> {

    @Query("SELECT pad FROM ProductAttributeDetail pad WHERE pad.productId = :productId")
    Iterable<ProductAttributeDetail> findAllByProductId(@Param("productId") long productId);

}
