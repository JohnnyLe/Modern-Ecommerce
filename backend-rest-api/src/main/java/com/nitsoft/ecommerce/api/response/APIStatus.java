package com.nitsoft.ecommerce.api.response;

public enum APIStatus {

    ERR_INVALID_DATA(100, "Input data is not valid."),
    ERR_USER_NOT_EXIST(110, "User does not exist"),
    ERR_USER_NOT_VALID(111, "User name or password is not correct"),
    USER_ALREADY_EXIST(112, "Email already exist"),
    USER_PENDING_STATUS(113, "User have not activated"),
    INVALID_PARAMETER(200, "Invalid request parameter"),
    TOKEN_EXPIRIED(202, "Token expiried"),
    REQUIRED_LOGIN(203, "Required login"),
    INVALID_TOKEN(204, "Invalid token"),
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
