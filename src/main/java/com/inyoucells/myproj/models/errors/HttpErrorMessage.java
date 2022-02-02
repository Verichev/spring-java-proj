package com.inyoucells.myproj.models.errors;

import com.fasterxml.jackson.annotation.JsonValue;

public enum HttpErrorMessage {

    EMPTY(""),
    EMAIL_IS_ALREADY_TAKEN("Email is already taken"),
    MISSING_DRIVER_ID("Driver id should be provided"),
    DRIVER_ID_NOT_FOUND("Driver id not found"),
    CAR_ID_NOT_FOUND("Driver id not found"),
    BAD_REQUEST("Bad request"),
    WRONG_CREDENTIALS("Wrong Credentials"),
    NOT_AUTHORISED("Not authorised operation"),
    AUTHORIZATION_IS_OUTDATED("Authorization is outdated"),
    FORBIDDEN("Forbidden"),
    NOT_FOUND("Not found"),
    SERVER_ERROR("Server error");

    private final String message;

    HttpErrorMessage(String message) {
        this.message = message;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpErrorMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
