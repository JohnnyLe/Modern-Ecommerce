/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.controller;

import com.nitsoft.ecommerce.api.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nitsoft.ecommerce.api.response.model.StatusResponse;
import com.nitsoft.ecommerce.auth.AuthUser;
import com.nitsoft.ecommerce.auth.service.CustomUserAuthService;
import com.nitsoft.ecommerce.configs.AppConfig;
import com.nitsoft.ecommerce.exception.ApplicationException;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.nitsoft.util.Constant;
import java.text.SimpleDateFormat;
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
    
    public AuthUser getAuthUserFromSession(HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        // try to load sessio
        AuthUser user = userDetailsService.loadUserByAccessToken(authToken);
        return user;
    }
}
