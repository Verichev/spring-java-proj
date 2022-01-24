package com.inyoucells.myproj.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @GetMapping(path = "/signup")
    ResponseEntity<Object> signup(String email, String pass) {
        return authService.signup(email, pass);
    }

    @GetMapping(path = "/authorize")
    ResponseEntity<Object> signin(String email, String pass) {
        return authService.signin(email, pass);
    }
}
