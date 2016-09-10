/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.ProductAttribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NHU LINH
 */
public interface ProductAttributeRepository extends CrudRepository<ProductAttribute, Long>{
    
    @Query("SELECT pad FROM ProductAttribute pad WHERE pad.attributeId = :attributeId")
    Iterable<ProductAttribute> findAllByProductAttribute(@Param("attributeId") long attributeId);
}
