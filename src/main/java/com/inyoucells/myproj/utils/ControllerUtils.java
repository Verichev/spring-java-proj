package com.inyoucells.myproj.utils;

import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.errors.ApiError;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.service.auth.TokenValidator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j
@Service
public class ControllerUtils {
    private final TokenValidator tokenValidator;

    @Autowired
    public ControllerUtils(TokenValidator tokenValidator) {
        this.tokenValidator = tokenValidator;
    }

    public <T extends ControllerResponse> ResponseEntity<ControllerResponse> authorizedFunction(String token, ServiceFunction<Long, T> action) {
        return rawAuthorizedFunction(token, userId -> {
            T result = action.apply(userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        });
    }

    public ResponseEntity<ControllerResponse> authorizedConsumer(String token, ServiceConsumer<Long> action) {
        return rawAuthorizedFunction(token, userId -> {
            action.accept(userId);
            return new ResponseEntity<>(HttpStatus.OK);
        });
    }

    public ResponseEntity<ControllerResponse> rawAuthorizedFunction(String token, ServiceFunction<Long, ResponseEntity<ControllerResponse>> action) {
        try {
            long userId = tokenValidator.parseUserId(token);
            return action.apply(userId);
        } catch (Throwable exception) {
            log.error(exception);
            ApiError apiError = mapToApiError(exception);
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    public ResponseEntity<ControllerResponse> rawUnauthorizedCallable(ServiceCallable<ResponseEntity<ControllerResponse>> action) {
        try {
            return action.call();
        } catch (Throwable exception) {
            log.error(exception);
            ApiError apiError = mapToApiError(exception);
            return new ResponseEntity<>(apiError, apiError.getStatus());
        }
    }

    private ApiError mapToApiError(Throwable throwable) {
        if (throwable instanceof ServiceError) {
            ServiceError serviceError = (ServiceError) throwable;
            return new ApiError(serviceError.getHttpStatus(), serviceError.getHttpError());
        } else {
            return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, HttpErrorMessage.SERVER_ERROR);
        }
    }

    @FunctionalInterface
    public interface ServiceFunction<T, R> {

        R apply(T t) throws ServiceError;
    }

    @FunctionalInterface
    public interface ServiceConsumer<T> {

        void accept(T t) throws ServiceError;
    }

    @FunctionalInterface
    public interface ServiceCallable<V> {

        V call() throws ServiceError;
    }
}
