/*
 * Copyright (c) NIT-Software. All Rights Reserved.
 * This software is the confidential and proprietary information of NIT-Software,
 * ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the license agreement you entered into with NIT-Software.
 */
package com.nitsoft.ecommerce.api.response;

import com.nitsoft.util.Constant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;

@Component
public class ResponseUtil {

    private StatusResponse _createSSOResponse(APIStatus apiStatus, Object data) {
        return new StatusResponse(apiStatus, data);
    }

    // base method
    public ResponseEntity<StatusResponse> buildResponse(APIStatus apiStatus, Object data, HttpStatus httpStatus) {
        return new ResponseEntity(_createSSOResponse(apiStatus, data), httpStatus);
    }

    public ResponseEntity<StatusResponse> successResponse(Object data) {
        return buildResponse(APIStatus.OK, data, HttpStatus.OK);
    }

    public ResponseEntity<StatusResponse> badRequestResponse(List<Constant.ParamError> errors) {

        Map<String, String> errMap = null;

        if (errors != null) {

            errMap = new HashMap<>();
            for (Constant.ParamError error : errors) {
                errMap.put(error.getName(), error.getDesc());
            }
        }

        return buildResponse(APIStatus.ERR_BAD_REQUEST, errMap, HttpStatus.BAD_REQUEST);
    }
}
