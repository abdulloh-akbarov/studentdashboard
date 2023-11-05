package com.zero.studentdashboard.model.enums;

import lombok.Getter;

@Getter
public enum Message {
    SUCCESS(200, "SUCCESS"),
    AUTHENTICATION_FAILED(4401, "AUTHENTICATION FAILED"),
    UNPROCESSABLE(422, "UNPROCESSABLE"),
    CANNOT_BE_NULL(4000, "CANNOT BE NULL"),
    EMAIL_EXIST(408, "EMAIL EXIST"),
    USERNAME_EXIST(4009, "USERNAME EXIST"),
    USER_NOT_FOUND(4050, "USER NOT FOUND"),
    INVALID_ACCESS_TOKEN(4403, "INVALID ACCESS TOKEN");

    private final Integer status;
    private final String message;

    Message(Integer status, String message) {
        this.status = status;
        this.message = message;
    }
}
