package com.nitsoft.ecommerce.service.product;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.ecommerce.database.model.ProductCategoryId;
import com.nitsoft.ecommerce.repository.ProductRepository;
import com.nitsoft.ecommerce.repository.specification.ProductSpecification;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // currently this method is implement just for testing
    @Override
    public Iterable<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(long companyId, long productId) {
        return productRepository.findOne(productId);
    }

//    @Override
//    public List<Object[]> getProductById(long productId) {
////        return productRepository.findByProductId(productId);
//        return null;
//    }

    @Override
    public Page<Product> getByCompanyId(long companyId, int pageNumber, int pageSize) {
        return productRepository.findByCompanyId(companyId, new PageRequest(pageNumber, pageSize));
    }

    @Override
    public Page<Product> getByCompanyIdAndCategoryId(long companyId, long categoryId, int pageNumber, int pageSize) {
        return null;
//        return productRepository.findByCategoryId(companyId, categoryId, new PageRequest(pageNumber, pageSize));
    }

    @Override
    public Page<Product> doFilterSearchSortPagingProduct(long comId, long catId, long attrId, String searchKey, double mnPrice, double mxPrice, int minRank, int maxRank, int sortKey, boolean isAscSort, int pSize, int pNumber) {
        return productRepository.findAll(new ProductSpecification(comId, catId, attrId, searchKey, mnPrice, mxPrice, minRank, maxRank, sortKey, isAscSort), new PageRequest(pNumber, pSize));
    }

    @Override
    public Iterable<Product> getProductsById(long companyId, List<Long> productIds) {
        return productRepository.findByProductIds(companyId, productIds);
    }

    @Override
    public Product save(Product product) {
        product.setProductId(null);
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void saveProductCategory(ProductCategory product) {
//        productRepository.saveProductCategory(product.getProductId(), product.getCategoryId());
    }

    @Override
    public void deleteProductCategory(ProductCategory product) {
//        productRepository.deleteProductCategory(product.getProductId());
    }
}
