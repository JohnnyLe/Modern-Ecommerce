/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VS9 X64Bit
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // currently this method is implement just for testing
    public Iterable<Product> findAllProduct() {
        return productRepository.findAll();
    }

    public Iterable<Product> findAllByCategoryId(long categoryId) {
        return productRepository.findAllByCategoryId(categoryId);
    }
}
