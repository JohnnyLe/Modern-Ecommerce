/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.controller;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.request.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.StatusResponse;
import com.nitsoft.ecommerce.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Quy Duong
 */
@RestController
@RequestMapping(APIName.AUTH_API)
public class AuthController {
    @Autowired
    AuthService authService;
    
    @RequestMapping(path = APIName.ADMIN_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<StatusResponse> adminLogin (
            @PathVariable("company_id") Long companyId,
            @RequestBody AuthRequestModel authRequestModel
    ){
        return authService.adminLogin(companyId, authRequestModel);
    }
}
