package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.ControllerResponse;
import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.inyoucells.myproj.utils.ResponseUtils.withError;
import static com.inyoucells.myproj.utils.ResponseUtils.withPayload;

@RestController("auth")
public class AuthController {

    private final AuthService authService;
    private final ControllerUtils controllerUtils;

    @Autowired
    public AuthController(AuthService authService, ControllerUtils controllerUtils) {
        this.authService = authService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping(path = "/signup")
    ResponseEntity<ControllerResponse> signup(String email, String pass) {
        return controllerUtils.rawUnauthorizedCallable(() -> {
            Optional<String> token = authService.signup(email, pass);
            if (token.isEmpty()) {
                return withError(HttpStatus.BAD_REQUEST, HttpErrorMessage.EMAIL_IS_ALREADY_TAKEN);
            } else {
                return wrapToken(token.get());
            }
        });
    }

    @GetMapping(path = "/authorize")
    ResponseEntity<ControllerResponse> signin(String email, String pass) {
        return controllerUtils.rawUnauthorizedCallable(() -> {
            Optional<String> token = authService.signin(email, pass);
            if (token.isEmpty()) {
                return withError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS);
            } else {
                return wrapToken(token.get());
            }
        });
    }

    ResponseEntity<ControllerResponse> wrapToken(String token) {
        return withPayload(token);
    }
}
