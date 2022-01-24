package com.inyoucells.myproj.service.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    ResponseEntity<Object> signup(String email, String pass);

    ResponseEntity<Object> signin(String email, String pass);
}
