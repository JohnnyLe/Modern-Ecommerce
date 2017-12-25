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
import com.nitsoft.ecommerce.service.AbstractBaseService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.xml.ws.Response;
import org.springframework.data.domain.Page;

/**
 * @author Quy Duong
 */
@Component
public class CategoriesServiceImpl extends AbstractBaseService implements CategoriesService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CompanyRepository companyRepository;

    public ResponseEntity<APIResponse> addCategory(CreateCategoryRequestModel categoryModel) {

        if (categoryModel == null) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }
//        int companyId = (int) categoryModel.getCompanyId();
//
//        Company company = companyRepository.findByCompanyId(companyId);
//
//        if (company == null) {
//            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
//        }

        Category category = new Category();
//        category.setCompanyId(categoryModel.getCompanyId());
        category.setParentId(categoryModel.getParentId());
        category.setName(categoryModel.getName());
        category.setStatus(categoryModel.getStatus());
        category.setPosition(categoryModel.getPosition());
        category.setDescription(categoryModel.getDescription());

        categoryRepository.save(category);

//        return ResponseUtil.successResponse(category);
        return null;
    }

    @Override
    public Category saveOrUpdate(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Category category) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(List<Category> categories) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Category getActiveById(long categoryId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Page<Category> getAllActiveWithFilterSearchSort(long companyId, String keyword, int pageNumber, int pageSize, int sortKey) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
