/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service.categories;

import com.nitsoft.ecommerce.api.request.model.CreateCategoryRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.api.response.util.ResponseUtil;
import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.database.model.Company;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.repository.CategoryRepository;
import com.nitsoft.ecommerce.repository.CompanyRepository;
import com.nitsoft.ecommerce.repository.specification.CategorySpecifications;
import com.nitsoft.ecommerce.service.AbstractBaseService;

import java.util.List;

import com.nitsoft.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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
    public List<Category> saveOrUpdate(List<Category> categories) {
        return (List<Category>) categoryRepository.save(categories);
    }

    @Override
    public Category getActiveById(long categoryId) {
        return categoryRepository.findByCategoryIdAndStatus(categoryId, 1);
    }

    @Override
    public List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId) {
        return categoryRepository.findAllByCategoryIdInAndCompanyIdAndStatus(categoryIds, companyId, 1);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Page<Category> getAllActiveWithFilterSearchSort(long companyId, String keyword, int pageNumber, int pageSize, int sortCase, boolean ascSort) {
        Pageable pageable = new PageRequest(pageNumber - 1, pageSize);

        // create specification
        Specification spec = categorySpecifications.doFilterSearchSort(companyId, keyword, sortCase, ascSort);
        return categoryRepository.findAll(spec, pageable);
    }

}
