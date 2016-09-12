package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.repository.ProductRepository;
import com.nitsoft.ecommerce.repository.ProductSecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Iterable<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey, double mnPrice, double mxPrice, int sortKey, boolean isAscSort, int pSize, int pNumber) {
        return productRepository.findAll(new ProductSecification(comId, catId, attrId, searchKey, mnPrice, mxPrice, sortKey, isAscSort));
    }

}
