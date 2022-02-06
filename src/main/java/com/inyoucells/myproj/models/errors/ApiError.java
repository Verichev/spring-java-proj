package com.inyoucells.myproj.models.errors;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class ApiError {

    private HttpStatus status;
    private HttpErrorMessage message;

    public ApiError(HttpStatus status, HttpErrorMessage message) {
        this.status = status;
        this.message = message;
    }
}