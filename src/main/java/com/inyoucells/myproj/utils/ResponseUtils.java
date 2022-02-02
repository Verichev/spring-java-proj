package com.inyoucells.myproj.utils;

import com.inyoucells.myproj.models.BasicPayload;
import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    public static <T extends ControllerResponse> ResponseEntity<ControllerResponse> withResponse(T payload) {
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

    public static <T> ResponseEntity<ControllerResponse> withPayload(T payload) {
        return new ResponseEntity<>(new BasicPayload<>(payload), HttpStatus.OK);
    }

    public static <T> ControllerResponse basicPayload(T payload) {
        return new BasicPayload<>(payload);
    }

    public static <T extends ControllerResponse> ResponseEntity<ControllerResponse> withError(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    public static <T extends ControllerResponse> ResponseEntity<ControllerResponse> withError(HttpStatus status, HttpErrorMessage message) {
        return new ResponseEntity<>(new ApiError(status, message), status);
    }
}
