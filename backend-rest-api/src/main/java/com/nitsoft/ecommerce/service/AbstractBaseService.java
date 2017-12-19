/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.service;

import com.google.gson.Gson;
import com.nitsoft.ecommerce.api.response.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Quy Duong
 */
public abstract class AbstractBaseService {
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    
    protected final Gson gson = new Gson();
    
    @Autowired
    protected ResponseUtil responseUtil;
}
