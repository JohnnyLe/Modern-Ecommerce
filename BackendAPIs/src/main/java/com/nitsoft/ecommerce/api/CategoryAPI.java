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
import com.nitsoftware.ecommerce.repository.CategoryRepository;
import com.reus.SpringBootDemo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
//@ComponentScan
//@RestController
//@EnableAutoConfiguration
//public class CategoryAPI 
//{
////    public static void main( String[] args )
////    {
////    	SpringApplication.run(SpringBootDemo.class, args);
////    }
//    @Autowired
//	CategoryRepository repository;
//
//	@RequestMapping(value= "api/category/list" ,method=RequestMethod.GET)
//	
//	public List<Category> get(){
//		return (List<Category>) repository.findAll();
//	}
}