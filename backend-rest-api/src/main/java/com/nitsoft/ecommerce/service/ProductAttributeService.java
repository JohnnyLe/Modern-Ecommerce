/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.ProductAttributeDetailRepository;

/**
 *
 * @author VS9 X64Bit
 */
@Service
public class ProductAttributeService {
    
    @Autowired
    private ProductAttributeDetailRepository productAttributeDetailRepository;
    
    public Iterable<ProductAttributeDetail> findAllByProductId(long productId) {
        return productAttributeDetailRepository.findAllByProductId(productId);
    }
}
