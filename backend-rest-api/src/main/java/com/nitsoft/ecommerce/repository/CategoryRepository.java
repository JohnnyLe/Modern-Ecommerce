package com.nitsoft.ecommerce.repository;

import com.nitsoft.ecommerce.database.model.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    Category findByCategoryId(@Param("categoryId") Long categoryId);

    Iterable<Category> findByCompanyId(@Param("companyId") Long companyId);

    Category findByCategoryIdAndStatus(long categoryId, int status);

    List<Category> findAllByCategoryIdInAndCompanyIdAndStatus(List<Long> categoryIds, long companyId, int status);

}
