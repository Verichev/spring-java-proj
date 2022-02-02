package com.inyoucells.myproj.models.errors;

import com.inyoucells.myproj.models.ControllerResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError implements ControllerResponse {

    private HttpStatus status;
    private HttpErrorMessage message;

    public ApiError(HttpStatus status, HttpErrorMessage message) {
        this.status = status;
        this.message = message;
    }
}