package com.inyoucells.myproj.utils;

import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.auth.TokenValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class ControllerUtils {
    private final TokenValidator tokenValidator;

    @Autowired
    public ControllerUtils(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public <T> ResponseEntity<T> authorizedFunction(String token, ServiceFunction<Long, T> action) {
        return rawAuthorizedFunction(token, userId -> {
            T result = action.apply(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    public <T> ResponseEntity<T> authorizedConsumer(String token, ServiceConsumer<Long> action) {
        return rawAuthorizedFunction(token, userId -> {
            action.accept(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    public <T> ResponseEntity<T> rawAuthorizedFunction(String token, ServiceFunction<Long, ResponseEntity<T>> action) {
        long userId = tokenValidator.parseUserId(token);
        return action.apply(userId);
    }

    @FunctionalInterface
    public interface ServiceFunction<T, R> {

        R apply(T t) throws ServiceError;
    }

    @FunctionalInterface
    public interface ServiceConsumer<T> {

        void accept(T t) throws ServiceError;
    }
}
