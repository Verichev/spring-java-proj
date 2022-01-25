package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.models.HttpError;
import com.inyoucells.myproj.utils.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    ResponseEntity<Object> signup(String email, String pass) {
        return controllerUtils.rawUnauthorizedCallable(() -> {
            Optional<String> token = authService.signup(email, pass);
            if (token.isEmpty()) {
                return new ResponseEntity<>(
                        HttpError.EMAIL_IS_ALREADY_TAKEN, HttpError.EMAIL_IS_ALREADY_TAKEN.getStatus());
            } else {
                return wrapToken(token.get());
            }
        });
    }

    @GetMapping(path = "/authorize")
    ResponseEntity<Object> signin(String email, String pass) {
        return controllerUtils.rawUnauthorizedCallable(() -> {
            Optional<String> token = authService.signin(email, pass);
            if (token.isEmpty()) {
                return new ResponseEntity<>(
                        HttpError.WRONG_CREDENTIALS, HttpError.WRONG_CREDENTIALS.getStatus());
            } else {
                return wrapToken(token.get());
            }
        });
    }

    ResponseEntity<Object> wrapToken(String token) {
        return new ResponseEntity<>(
                token, new HttpHeaders(), HttpStatus.OK);
    }
}
