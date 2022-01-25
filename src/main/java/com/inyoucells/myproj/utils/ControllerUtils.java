package com.inyoucells.myproj.utils;

import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.models.TokenValidationResult;
import com.inyoucells.myproj.service.auth.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;

@Service
public class ControllerUtils {
    private final TokenValidator tokenValidator;

    @Autowired
    public ControllerUtils(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public <T> ResponseEntity<Object> authorizedFunction(String token, Function<Long, T> action) {
        return rawAuthorizedFunction(token, userId -> {
            T result = action.apply(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    public ResponseEntity<Object> authorizedConsumer(String token, Consumer<Long> action) {
        return rawAuthorizedFunction(token, userId -> {
            action.accept(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    public ResponseEntity<Object> rawAuthorizedFunction(String token, Function<Long, ResponseEntity<Object>> action) {
        TokenValidationResult validationResult = tokenValidator.check(token);
        if (validationResult.getApiError() != HttpError.EMPTY) {
            return new ResponseEntity<>(
                    validationResult.getApiError(), validationResult.getApiError().getStatus());
        }
        try {
            return action.apply(validationResult.getUserId());
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpError.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Object> rawUnauthorizedCallable(Callable<ResponseEntity<Object>> action) {
        try {
            return action.call();
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpError.SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
