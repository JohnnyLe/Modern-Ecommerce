/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service.categories;

import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import com.nitsoft.ecommerce.repository.specification.CategorySpecifications;
import com.nitsoft.ecommerce.service.AbstractBaseService;
import com.nitsoft.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tungn
 */
@Service
public class CategoriesServiceImpl extends AbstractBaseService implements CategoriesService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private CategorySpecifications categorySpecifications;


    @Override
    public Category saveOrUpdate(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public void delete(List<Category> categories) {
        categoryRepository.delete(categories);
    }

    @Override
    public Category getActiveById(long categoryId) {
        return categoryRepository.findByCategoryIdAndStatus(categoryId, Constant.STATUS.ACTIVE_STATUS.getValue());
    }

    @Override
    public List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId) {
        return categoryRepository.findAllByCategoryIdInAndCompanyIdAndStatus(categoryIds, companyId, Constant.STATUS.ACTIVE_STATUS.getValue());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Category> getAllActiveWithFilterSearchSort(long companyId, String keyword, int pageNumber, int pageSize, int sortKey) {
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize);

        // create specification
        Specification spec = categorySpecifications.doFilterSearchSort(companyId, keyword, sortKey);
        return categoryRepository.findAll(spec, pageable);
    }

}
