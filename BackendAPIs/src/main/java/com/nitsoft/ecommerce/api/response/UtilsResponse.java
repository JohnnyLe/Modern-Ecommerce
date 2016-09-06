/*
 */
package com.nitsoft.ecommerce.api.response;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * @Class Name: JsonResponse.java
 * @brief: This class is implementation for creating Json response template.
 */
@Component
public class UtilsResponse {

    public UtilsResponse() {
    }

    StatusResponse response;

    /**
     *
     * @param result
     * @param desciption
     * @return
     */
    public StatusResponse successResponse(String result, String desciption) {
        response = new StatusResponse();
        response.setResult(result);
        response.setStatusCode(APIStatus.OK.getCode());
        response.setDescription(desciption);
        return response;
    }

    public StatusResponse successResponse(Object result, String desciption) {
        response = new StatusResponse();
        response.setResult(result);
        response.setStatusCode(APIStatus.OK.getCode());
        response.setDescription(desciption);
        return response;
    }
    
    public StatusResponse successResponse(Object result, String desciption, Long totalRecords) {
        response = new StatusResponse();
        response.setResult(result);
        response.setStatusCode(APIStatus.OK.getCode());
        response.setDescription(desciption);
        response.setTotalRecords(totalRecords);
        response.setServerTime(new Date().getTime());
        return response;
    }
    /**
     *
     * @param errorCode
     * @param result
     * @param desciption
     * @return
     */
    public StatusResponse errorResponse(int errorCode, String result, String desciption) {
        response = new StatusResponse();
        response.setResult(result);
        response.setStatusCode(errorCode);
        response.setDescription(desciption);
        return response;
    }
    
    public StatusResponse errorResponse(String desciption) {
        response = new StatusResponse();
        response.setResult(APIStatus.INVALID_PARAMETER.getDescription());
        response.setStatusCode(APIStatus.INVALID_PARAMETER.getCode());
        response.setDescription(desciption);
        return response;
    }

}
