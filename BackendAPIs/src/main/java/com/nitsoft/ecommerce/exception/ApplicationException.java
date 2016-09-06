package com.nitsoft.ecommerce.exception;

import com.nitsoft.ecommerce.api.response.APIStatus;

// Unchecked exception
public class ApplicationException extends RuntimeException {

    private int errorCode = 0;
    private String desctiption;

    public ApplicationException(APIStatus apiStatus) {
        super(apiStatus.getDescription());
        setAPIStatus(apiStatus);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(APIStatus apiStatus, String message) {
        super(message);
        setAPIStatus(apiStatus);
    }

    public ApplicationException(APIStatus apiStatus, Throwable cause) {
        super(apiStatus.getDescription(), cause);
        setAPIStatus(apiStatus);
    }

    public ApplicationException(APIStatus apiStatus, String message, Throwable cause) {
        super(message, cause);
        setAPIStatus(apiStatus);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDescription() {
        return desctiption;
    }

    private void setAPIStatus(APIStatus apiStatus) {
        this.errorCode = apiStatus.getCode();
        this.desctiption = apiStatus.getDescription();
    }

}
