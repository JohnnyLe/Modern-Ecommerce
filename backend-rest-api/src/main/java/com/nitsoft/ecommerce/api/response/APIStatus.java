/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nitsoft.ecommerce.api.response;

/**
 * This class defines all error code for HoGo pro version
 *
 */
public enum APIStatus {

    ERR_INVALID_DATA(100, "Input data is not valid."),
    ERR_USER_NOT_EXIST(110, "User does not exist"),
    ERR_USER_NOT_VALID(111, "User name or password is not correct"),
    USER_ALREADY_EXIST(112, "Email already exist"),
    INVALID_PARAMETER(200, "Invalid request parameter"),
    // Common status
    OK(200, null);

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
