package com.inyoucells.myproj.models;

import lombok.Data;

@Data
public class BasicPayload<T> implements ControllerResponse {
    private final T response;

    public BasicPayload(T response) {
        this.response = response;
    }
}
