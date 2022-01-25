package com.inyoucells.myproj.models;

import lombok.Data;

@Data
public class TokenValidationResult {
    private final Long userId;
    private final HttpError apiError;
}
