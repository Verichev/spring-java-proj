package com.inyoucells.myproj.models;

import lombok.Data;

@Data
public class ServiceError extends Exception {

    private final HttpError httpError;

    public ServiceError(HttpError httpError) {
        this.httpError = httpError;
    }

    @Override
    public String getMessage() {
        return httpError.getMessage();
    }

    public HttpError getHttpError() {
        return httpError;
    }
}
