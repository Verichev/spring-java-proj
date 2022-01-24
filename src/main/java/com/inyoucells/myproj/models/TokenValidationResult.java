package com.inyoucells.myproj.models;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class TokenValidationResult {
    @Nullable
    private final Long userId;
    @Nullable
    private final ApiError apiError;
}
