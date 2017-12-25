package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Product;
import com.nitsoft.ecommerce.database.model.ProductCategory;
import com.nitsoft.ecommerce.database.model.ProductCategoryId;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    Page<Product> findByCompanyId(@Param("companyId") long companyId, Pageable pageable);

//    @Query("SELECT p FROM Product p, ProductCategory pc WHERE p.companyId = :companyId AND pc.categoryId = :categoryId AND pc.productId = p.productId")
//    Page<Product> findByCategoryId(@Param("companyId") long companyId, @Param("categoryId") long categoryId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.companyId = :companyId AND p.productId IN (:productIds)")
    Iterable<Product> findByProductIds(@Param("companyId") long companyId, @Param("productIds") List<Long> productIds);
    
//    @Query("SELECT p FROM ProductCategory p WHERE p.productId = :productId")
//    Iterable<ProductCategory> findByProductId(@Param("productId") long productId);
//    
//    @Query("DELETE FROM ProductCategory p WHERE p.productId = :productId")
//    void deleteProductCategory(@Param("productId") long productId);
//    
//    @Query(value = "SELECT `category_id` FROM `product_categories` WHERE `product_id` = :productId", nativeQuery = true)
//    List<Object[]> findByProductId(@Param("productId") long productId);

}
