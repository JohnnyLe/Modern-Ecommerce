/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.controller;

import com.nitsoft.ecommerce.api.response.util.ResponseUtil;
import com.nitsoft.ecommerce.auth.AuthUser;
import com.nitsoft.ecommerce.auth.service.CustomUserAuthService;
import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.util.Constant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Setting up some stuff using for all API
 *
 */
public abstract class AbstractBaseController {
    
    @Autowired
    private CustomUserAuthService userDetailsService;

    @Autowired
    AppConfig appConfig;
    
    @Autowired
    protected ResponseUtil responseUtil;
    
    public AuthUser getAuthUserFromSession(HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        // try to load sessio
        AuthUser user = userDetailsService.loadUserByAccessToken(authToken);
        return user;
    }
}
