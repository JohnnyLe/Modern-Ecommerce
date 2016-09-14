package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.ProductAttributeDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nitsoft.ecommerce.repository.ProductAttributeDetailRepository;

@Service
public class ProductAttributeDetailService {

    @Autowired
    private ProductAttributeDetailRepository productAttributeDetailRepository;

    public Iterable<ProductAttributeDetail> findAllByProductId(long productId) {
        return productAttributeDetailRepository.findAllByProductId(productId);
    }

    public ProductAttributeDetail findByProductIdAndAttributeId(long productId, long attributeId) {
        return productAttributeDetailRepository.findByProductIdAndAttributeId(productId, attributeId);
    }

}
