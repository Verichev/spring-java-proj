package com.inyoucells.myproj.models.errors;

import org.springframework.http.HttpStatus;

public enum TypicalError {
    WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS),
    EMAIL_IS_ALREADY_TAKEN(HttpStatus.BAD_REQUEST, HttpErrorMessage.EMAIL_IS_ALREADY_TAKEN),
    MISSING_DRIVER_ID(HttpStatus.BAD_REQUEST, HttpErrorMessage.MISSING_DRIVER_ID),
    DRIVER_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, HttpErrorMessage.DRIVER_ID_NOT_FOUND),
    CAR_ID_NOT_FOUND(HttpStatus.BAD_REQUEST, HttpErrorMessage.CAR_ID_NOT_FOUND),
    NOT_AUTHORISED(HttpStatus.UNAUTHORIZED, HttpErrorMessage.NOT_AUTHORISED),
    AUTHORIZATION_IS_OUTDATED(HttpStatus.UNAUTHORIZED, HttpErrorMessage.AUTHORIZATION_IS_OUTDATED),
    FORBIDDEN(HttpStatus.FORBIDDEN, HttpErrorMessage.FORBIDDEN),
    NOT_FOUND(HttpStatus.NOT_FOUND, HttpErrorMessage.NOT_FOUND),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, HttpErrorMessage.SERVER_ERROR);

    private final HttpStatus status;
    private final HttpErrorMessage message;

    TypicalError(HttpStatus status, HttpErrorMessage message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public HttpErrorMessage getMessage() {
        return message;
    }
}
