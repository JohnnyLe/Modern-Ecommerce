/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.service.auth;

import com.nitsoft.ecommerce.api.request.AuthRequestModel;
import com.nitsoft.ecommerce.api.response.APIResponse;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author Quy Duong
 */
public interface AuthService {
    // Admin log
    ResponseEntity<APIResponse> adminLogin(Long companyId, AuthRequestModel authRequestModel);
    
    // Get authen user info (session data)
    ResponseEntity<APIResponse> getUserData (Long companyId, String userId);
    
    // User Logout
    ResponseEntity<APIResponse> logout (String tokenId);
}
