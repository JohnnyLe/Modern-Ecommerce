/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.auth.service;

import com.nitsoft.ecommerce.auth.AuthUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author Quy Duong
 */
public interface CustomUserAuthService extends UserDetailsService{
    AuthUser loadUserByAccessToken(String token);
}
