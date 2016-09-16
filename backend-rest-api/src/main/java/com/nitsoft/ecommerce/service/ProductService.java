package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.repository.ProductRepository;
import com.nitsoft.ecommerce.repository.specification.ProductSpecification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // currently this method is implement just for testing
    public Iterable<Product> findAllProduct() {
        return productRepository.findAll();
    }
    
    public Product getProductById(long companyId, long productId) {
        return productRepository.findOne(productId);
    }

    public Page<Product> getByCompanyId(long companyId, int pageNumber, int pageSize) {
        return productRepository.findByCompanyId(companyId, new PageRequest(pageNumber, pageSize));
    }

    public Page<Product> getByCompanyIdAndCategoryId(long companyId, long categoryId, int pageNumber, int pageSize) {
        return productRepository.findByCategoryId(companyId, categoryId, new PageRequest(pageNumber, pageSize));
    }

    public Page<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey, double mnPrice, double mxPrice, int minRank, int maxRank, int sortKey, boolean isAscSort, int pSize, int pNumber) {
        return productRepository.findAll(new ProductSpecification(comId, catId, attrId, searchKey, mnPrice, mxPrice, minRank, maxRank, sortKey, isAscSort), new PageRequest(pNumber, pSize));
    }
    
    public Iterable<Product> getProductsById(long companyId, List<Long> productIds) {
        return productRepository.findByProductIds(companyId, productIds);
    }

}
