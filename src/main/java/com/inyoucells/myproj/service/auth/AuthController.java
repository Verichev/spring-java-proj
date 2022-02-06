package com.inyoucells.myproj.service.auth;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.models.errors.HttpErrorMessage;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.response.TokenResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping(path = "/signup")
    ResponseEntity<TokenResponse> signup(String email, String pass) {
        Optional<String> token = authService.signup(email, pass);
        if (token.isEmpty()) {
            throw new ServiceError(HttpStatus.BAD_REQUEST, HttpErrorMessage.EMAIL_IS_ALREADY_TAKEN);
        } else {
            return wrapToken(token.get());
        }
    }

    @GetMapping(path = "/login")
    ResponseEntity<TokenResponse> signin(String email, String pass) {
        Optional<String> token = authService.signin(email, pass);
        if (token.isEmpty()) {
            throw new ServiceError(HttpStatus.UNAUTHORIZED, HttpErrorMessage.WRONG_CREDENTIALS);
        } else {
            return wrapToken(token.get());
        }
    }

    ResponseEntity<TokenResponse> wrapToken(String token) {
        return withResponse(new TokenResponse(token));
    }
}
