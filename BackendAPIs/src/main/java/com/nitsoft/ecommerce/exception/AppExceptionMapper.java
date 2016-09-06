/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.exception;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nitsoft.ecommerce.api.response.APIStatus;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.tracelogged.EventLogManager;
import com.nitsoft.manager.HibernateSessionManager;
import java.io.IOException;
import java.text.ParseException;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

/**
 *
 */
@ControllerAdvice
public class AppExceptionMapper {

    // extends ResponseEntityExceptionHandler. Using for return only
    //
    // Build setting for Gson class
    //
    Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

    //
    // Create logger
    //
    public final static EventLogManager logger = EventLogManager.getInstance();

    //
    // Handle checked exception
    //
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected String handleAllException(Exception ex) {
        // Write log
        logger.error(ex.getMessage(), ex);
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
//        return new ResponseEntity<String>(ex.getMessage(), responseHeaders, HttpStatus.CREATED);
        StatusResponse status = new StatusResponse(500, ex.getMessage());
        return gson.toJson(status);
    }

    //
    // We handle unchecked exception here to return a custom data (RuntimeException)
    //
    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    protected String handleAllAppException(ApplicationException ex) {
        // Write log
        logger.error(ex.getMessage(), ex);
        StatusResponse status;
        if (ex.getErrorCode() > 0) {
            status = new StatusResponse(ex.getErrorCode(), ex.getMessage());
        } else {
            status = new StatusResponse(APIStatus.SYSTEM_EXCEPTION.getCode(), ex.getMessage());
        }
        return gson.toJson(status);
    }

    //
    // Handle missing parameter
    //
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public String handleMissingParamterHandler(MissingServletRequestParameterException ex) {
        // Create json
        StatusResponse status = new StatusResponse(APIStatus.MISSING_PARAMETER.getCode(), "Parameter " + ex.getParameterName() + " missing");
        return gson.toJson(status);
    }
    
//    @ExceptionHandler(ParseException.class)
//    @ResponseBody
//    public String handleDateFormatException(ParseException ex) {
//        // Create json
//        StatusResponse status = new StatusResponse(APIStatus..getCode(), "Parameter " + ex.get + " missing");
//        return gson.toJson(status);
//    }

    @ExceptionHandler(HibernateException.class)
    @ResponseBody
    public String handleHibernateException(HibernateException ex) {
        // Write log
        logger.error(ex.getMessage(), ex);
        // Close connection first
        HibernateSessionManager.getSessionFactory().getCurrentSession().close();
        // Create json
        StatusResponse status = new StatusResponse(APIStatus.SYSTEM_EXCEPTION.getCode(), ex.getMessage());
        return gson.toJson(status);
    }
    
    
    
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public String handleIOExeption(IOException ex) {
        // Create json
        StatusResponse status = new StatusResponse(APIStatus.ERR_INVALID_DATA.getCode(),"File does not found or input data does not valid");
        return gson.toJson(status);
    }
    
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public String handleIOExeption(MultipartException ex) {
        // Create json
        StatusResponse status = new StatusResponse(APIStatus.ERR_INVALID_DATA.getCode(),"Not found File Data Upload, please check request file parameter or file input");
        return gson.toJson(status);
    }

}
