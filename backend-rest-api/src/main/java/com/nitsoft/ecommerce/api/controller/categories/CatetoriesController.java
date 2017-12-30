/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.controller.categories;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.controller.AbstractBaseController;
import com.nitsoft.ecommerce.api.request.model.CreateCategoryRequestModel;
import com.nitsoft.ecommerce.api.request.model.UpdateCategoryRequestModel;
import com.nitsoft.ecommerce.api.response.model.APIResponse;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.api.response.util.APIStatus;
import com.nitsoft.ecommerce.database.model.Category;
import com.nitsoft.ecommerce.database.model.Company;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.service.CompanyService;
import com.nitsoft.ecommerce.service.categories.CategoriesService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * @author tungn
 */

@RestController
@RequestMapping(APIName.CATEGORIES_API)
public class CatetoriesController extends AbstractBaseController {

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(path = APIName.CATEGORIES_ADD, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> addCategory(
            @PathVariable(value = "company_id") long companyId,
            @RequestBody @Valid CreateCategoryRequestModel categoryModel
    ) {

        Company company = companyService.findByCompanyId(companyId);

        if (company == null) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        Category category = new Category();
        category.setCompanyId(companyId);
        category.setParentId(categoryModel.getParentId());
        category.setName(categoryModel.getName());
        category.setStatus(1);
        category.setPosition(categoryModel.getPosition());
        category.setDescription(categoryModel.getDescription());

        categoriesService.saveOrUpdate(category);

        return responseUtil.successResponse(category);
    }

    @RequestMapping(path = APIName.CATEGORIES_UPDATE, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> updateCategory(
            @PathVariable(value = "company_id") long companyId,
            @RequestBody @Valid UpdateCategoryRequestModel categoryModel
    ) {

        Company company = companyService.findByCompanyId(companyId);

        if (company == null) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        Category category = categoriesService.getActiveById(categoryModel.getId());

        if (category == null || companyId != category.getCompanyId()) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        if (categoryModel.getParentId() != null) {
            Category parent = categoriesService.getActiveById(categoryModel.getParentId());
            if (parent != null && parent.getCompanyId().equals(category.getCompanyId())) {
                category.setParentId(categoryModel.getParentId());
            } else {
                throw new ApplicationException(APIStatus.INVALID_PARAMETER);
            }
        }

        category.setName(categoryModel.getName());
        category.setPosition(categoryModel.getPosition());
        category.setDescription(categoryModel.getDescription());

        categoriesService.saveOrUpdate(category);

        return responseUtil.successResponse(category);
    }

    @RequestMapping(value = APIName.CATEGORIES_DELETE, method = RequestMethod.POST, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteCategory(
            @PathVariable(value = "company_id") long companyId,
            @RequestBody List<Long> categoryIds
    ) {

        Company company = companyService.findByCompanyId(companyId);

        if (company == null) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        List<Category> categoryList = categoriesService.getAllActiveByIdsAndCompanyId(categoryIds, companyId);

        if (categoryList.isEmpty()) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        categoryList.forEach(category -> category.setStatus(0));

        categoriesService.saveOrUpdate(categoryList);

        return responseUtil.successResponse(null);

    }

    @RequestMapping(value = APIName.CATEGORIES_LIST, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> getCategories(
            @PathVariable(value = "company_id") long companyId,
            @RequestParam(value = "search_key", required = false) String search,
            @RequestParam(value = "sort_case", defaultValue = "1", required = false) int sortCase,
            @RequestParam(value = "asc_sort", defaultValue = "true", required = false) boolean ascSort,
            @RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "page_number", defaultValue = "1", required = false) int pageNumber
    ) {
        System.out.println("companyId = [" + companyId + "], search = [" + search + "], sortCase = [" + sortCase + "], ascSort = [" + ascSort + "], pageSize = [" + pageSize + "], pageNumber = [" + pageNumber + "]");

        Page<Category> categoryPage = categoriesService.getAllActiveWithFilterSearchSort(companyId, search, pageNumber, pageSize, sortCase, ascSort);

        return responseUtil.successResponse(categoryPage);

    }

    @RequestMapping(value = APIName.CATEGORIES_DETAIL, method = RequestMethod.GET, produces = APIName.CHARSET)
    public ResponseEntity<APIResponse> deleteCategory(
            @PathVariable(value = "company_id") long companyId,
            @PathVariable(value = "category_id") long categoryId
    ) {

        Company company = companyService.findByCompanyId(companyId);

        if (company == null) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        Category category = categoriesService.getActiveById(categoryId);

        if (category == null || category.getCompanyId() != companyId) {
            throw new ApplicationException(APIStatus.INVALID_PARAMETER);
        }

        return responseUtil.successResponse(category);

    }

}
