package com.nitsoft.ecommerce.api.response;

public enum APIStatus {

    ERR_INVALID_DATA(100, "Input data is not valid."),
    ERR_USER_NOT_EXIST(110, "User does not exist"),
    ERR_USER_NOT_VALID(111, "User name or password is not correct"),
    USER_ALREADY_EXIST(112, "Email already exist"),
    USER_PENDING_STATUS(113, "User have not activated"),
    INVALID_PARAMETER(200, "Invalid request parameter"),
    ERR_UNAUTHORIZED(401, "Unauthorized or Access Token is expired"),
    ERR_PERMISSION_DENIED (402, "Access Permission denied"),
    // Common status
    OK(200, null),
    ERR_INTERNAL_SERVER(500, "SQL Error"),
    SQL_ERROR(501, "SQL Error"),
    ERR_BAD_REQUEST(400, "Bad request"),
    ERR_SESSION_DATA_INVALID (603, "Session data invalid"),
    ERR_SESSION_NOT_FOUND(604, "Session not found");

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
