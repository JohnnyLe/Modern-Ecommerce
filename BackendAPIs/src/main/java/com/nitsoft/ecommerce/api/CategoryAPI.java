/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

import static com.nitsoft.ecommerce.api.APIUtil.mapper;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.api.response.UtilsResponse;
import com.nitsoft.ecommerce.dao.CategoryImpl;
import com.nitsoft.ecommerce.model.Category;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class CategoryAPI extends APIUtil {
        @Autowired
    private CategoryImpl categoryDAO;

    @Autowired
    private UtilsResponse utilResponse;
    
   
    @RequestMapping(value = APIName.GET_LIST, method = RequestMethod.GET ,produces = APIName.CHARSET)
    @ResponseBody
    public String getCategoryAPI(){
                
        List<Category> category = categoryDAO.findAll(Category.class);
        
        return writeObjectToJson(new StatusResponse<List<Category>>(200,category));
    }
}