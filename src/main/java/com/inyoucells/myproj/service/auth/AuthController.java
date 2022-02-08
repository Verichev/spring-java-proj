package com.inyoucells.myproj.service.auth;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.models.response.TokenResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return wrapToken(authService.signup(email, pass));
    }

    @GetMapping(path = "/login")
    ResponseEntity<TokenResponse> signin(String email, String pass) {
        return wrapToken(authService.signin(email, pass));
    }

    ResponseEntity<TokenResponse> wrapToken(String token) {
        return withResponse(new TokenResponse(token));
    }
}
