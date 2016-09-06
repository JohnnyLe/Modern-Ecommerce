/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.dao.UserSessionImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @Class Name: APIRequestInterceptor.java
 * @author: lxanh
 * @created: Oct 20, 2014
 * @version: 2.0
 *
 * @brief: This class is implementation for intercepting the request BEFORE it reaches the controller
 */

public class APIRequestInterceptor extends HandlerInterceptorAdapter {

   @Autowired
   private UserSessionImpl userSessionDao;  

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ApplicationException {
    
      // Do validating user Token for every request before transfer to process API
      //TODO
      return true;
    
  } 

   @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
        //TODO
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //TODO
    }
}
