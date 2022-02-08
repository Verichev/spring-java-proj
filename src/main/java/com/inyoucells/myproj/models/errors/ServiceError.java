package com.inyoucells.myproj.models.errors;

import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ServiceError extends RuntimeException {

    private final HttpStatus httpStatus;
    private final HttpErrorMessage httpError;

    public ServiceError(HttpStatus httpStatus, HttpErrorMessage httpError) {
        super(httpError.getMessage());
        this.httpStatus = httpStatus;
        this.httpError = httpError;
    }

    public ServiceError(TypicalError typicalError) {
        super(typicalError.getMessage().getMessage());
        this.httpStatus = typicalError.getStatus();
        this.httpError = typicalError.getMessage();
    }

    public HttpErrorMessage getHttpError() {
        return httpError;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    //used for tests
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceError that = (ServiceError) o;
        return httpStatus == that.httpStatus && httpError == that.httpError;
    }

    @Override
    public int hashCode() {
        return Objects.hash(httpStatus, httpError);
    }
}
