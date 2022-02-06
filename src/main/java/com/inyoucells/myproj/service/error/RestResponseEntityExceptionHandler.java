package com.inyoucells.myproj.service.error;

import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ServiceError.class})
    protected ResponseEntity<Object> handleException(
            ServiceError serviceError, WebRequest request) {
        ApiError apiError = new ApiError(serviceError.getHttpStatus(), serviceError.getHttpError());
        return handleExceptionInternal(serviceError, apiError,
                new HttpHeaders(), serviceError.getHttpStatus(), request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleGeneralException(
            Exception exception, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, HttpErrorMessage.SERVER_ERROR);
        return handleExceptionInternal(exception, apiError,
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}