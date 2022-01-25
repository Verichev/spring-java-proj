package com.inyoucells.myproj.models;

import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.http.HttpStatus;

public enum HttpError {

    EMPTY(HttpStatus.OK, ""),
    EMAIL_IS_ALREADY_TAKEN(HttpStatus.BAD_REQUEST, "Email is already taken"),
    MISSING_DRIVER_ID(HttpStatus.BAD_REQUEST, "Driver id should be provided"),
    WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Wrong Credentials"),
    AUTHORIZATION_IS_OUTDATED(HttpStatus.UNAUTHORIZED, "Authorization is outdated"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Forbidden"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server error");

    private final HttpStatus status;
    private final String message;

    HttpError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @JsonValue
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpError{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
