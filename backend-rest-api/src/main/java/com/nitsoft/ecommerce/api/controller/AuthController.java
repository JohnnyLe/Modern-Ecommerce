/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.controller;

import com.nitsoft.ecommerce.api.APIName;
import com.nitsoft.ecommerce.api.AbstractBaseAPI;
import com.nitsoft.ecommerce.api.request.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.APIResponse;
import com.nitsoft.ecommerce.service.auth.AuthService;
import com.nitsoft.util.Constant;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Quy Duong
 */
@RestController
@RequestMapping(APIName.AUTH_API)
public class AuthController extends AbstractBaseAPI{
    @Autowired
    AuthService authService;
    
    @RequestMapping(path = APIName.ADMIN_LOGIN_API, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> adminLogin (
            @PathVariable("company_id") Long companyId,
            @RequestBody AuthRequestModel authRequestModel
    ){
        return authService.adminLogin(companyId, authRequestModel);
    }
    
    @RequestMapping(path = APIName.SESSION_DATA, method = RequestMethod.GET)
    public ResponseEntity<APIResponse> getSessionData(
            HttpServletRequest request,
            @PathVariable("company_id") Long companyId
    ) {
        return authService.getUserData(companyId, getAuthUserFromSession(request).getId());
    }
    
    @RequestMapping(path = APIName.USERS_LOGOUT, method = RequestMethod.POST)
    public ResponseEntity<APIResponse> logout(
            @RequestHeader(value = Constant.HEADER_TOKEN) String tokenId
    ) {
        return authService.logout(tokenId);
    }
}
