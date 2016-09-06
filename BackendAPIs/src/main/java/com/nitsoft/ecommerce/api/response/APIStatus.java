/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.response;

/**
 *
 */
public enum APIStatus {
    
    ERR_INVALID_DATA(100, "Input data is not valid."),
    ERR_USER_NOT_EXIST(110, "User does not exist"),    
    ERR_USER_NOT_VALID(111, "User name or password is not correct"),
    ERR_USER_ROLE_NOT_VALID(112, "You do not have permission to access this page"),
    
    INVALID_ACCESS_TOKEN(301,"INVALID_ACCESS_TOKEN"),
    INVALID_PARAMETER(302,"INVALID_PARAMETER"),
    MISSING_PARAMETER (303,"MISSING_PARAMETER"),
    SYSTEM_EXCEPTION (500,"SYSTEM_EXCEPTION"),
    // Common status
    OK(200, null),

    //Oauth error
    API_KEY_NOT_VALID(800, "API key is not vallid");
    
    private final int code;
    private final String description;

    private APIStatus(int s, String v) {
        code = s;
        description = v;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
