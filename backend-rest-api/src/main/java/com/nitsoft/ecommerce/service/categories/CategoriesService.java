/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.service.categories;

import com.nitsoft.ecommerce.database.model.Category;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author tungn
 */
public interface CategoriesService {

    Category saveOrUpdate(Category category);

    List<Category> saveOrUpdate(List<Category> categories);

    Category getActiveById(long categoryId);

    List<Category> getAllActiveByIdsAndCompanyId(List<Long> categoryIds, long companyId);

    Page<Category> getAllActiveWithFilterSearchSort(long companyId, String keyword, int pageNumber, int pageSize, int sortCase, boolean ascSort);

}
