package com.inyoucells.myproj.service.auth;

import static com.inyoucells.myproj.utils.ResponseUtils.withResponse;

import com.inyoucells.myproj.service.auth.models.TokenResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("auth")
@Api(value = "authorisation")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation(value = "Registration", notes = "Register new user")
    @GetMapping(path = "/signup")
    ResponseEntity<TokenResponse> signup(String email, String pass) {
        return wrapToken(authService.signup(email, pass));
    }

    @ApiOperation(value = "Login", notes = "Login user")
    @GetMapping(path = "/login")
    ResponseEntity<TokenResponse> signin(String email, String pass) {
        return wrapToken(authService.signin(email, pass));
    }

    ResponseEntity<TokenResponse> wrapToken(String token) {
        return withResponse(new TokenResponse(token));
    }
}
