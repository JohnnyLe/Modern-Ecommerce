/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api;

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
public abstract class AbstractBaseAPI {
    
    @Autowired
    private CustomUserAuthService userDetailsService;

    //
    // Build setting for Gson class accept NULL value
    //
    Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(Constant.API_FORMAT_DATE).create();

    // Mapper object is used to convert object and etc...
    public final static ObjectMapper mapper = new ObjectMapper();

    

    static {
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES)
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .setDateFormat(new SimpleDateFormat(Constant.API_FORMAT_DATE));
    }

    @Autowired
    AppConfig appConfig;

    //
    // Create logger
    //
    public final static EventLogManager logger = EventLogManager.getInstance();

    //
    // Write object as string using mapper
    //
    protected String writeObjectToJson(Object obj) {
        try {

            return mapper.writeValueAsString(obj);

        } catch (JsonProcessingException ex) {
            // Throw our exception
            throw new ApplicationException(ex.getCause());
        }
    }

    protected String writeObjectToJsonRemoveNullProperty(Object obj) throws ApplicationException
    {    
         try {
             // Set setting remove NULL property
             mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
             // map json
             String result=mapper.writeValueAsString(obj);
             // Reset default setting
             mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
             return  result;

        } catch (JsonProcessingException ex) {
            // Throw our exception
            throw new ApplicationException(ex.getCause());
        }        
    }
    
    //
    // Reponse status
    //
    public StatusResponse statusResponse = null;

    // get current user authenticated
//    public User getcurrentUser() {
////        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
////        String authToken = request.getParameter("auth_token");
////        User currentUser = null;//userSessionDao.getUserBySessionID(authToken);
////        if (currentUser == null) {
////            throw new ApplicationException(APIStatus.INVALID_ACCESS_TOKEN);
////        }
////        return currentUser;
//      return null;
//    }
    
    public AuthUser getAuthUserFromSession(HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        // try to load sessio
        AuthUser user = userDetailsService.loadUserByAccessToken(authToken);
        return user;
    }
}
