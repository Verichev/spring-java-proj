package com.inyoucells.myproj.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtils {
    public static <T> ResponseEntity<T> withResponse(T payload) {
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }
}
