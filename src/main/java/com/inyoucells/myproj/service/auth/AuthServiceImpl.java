package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.data.UserRepo;
import com.inyoucells.myproj.models.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepo userRepo;

    @Override
    public ResponseEntity<Object> signup(String email, String pass) {
        String token = userRepo.addUser(email, pass);
        if (token == null) {
            ApiError apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "The email is already taken");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        } else {
            return wrapToken(token);
        }
    }

    @Override
    public ResponseEntity<Object> signin(String email, String pass) {
        String token = userRepo.checkUser(email, pass);
        if (token == null) {
            ApiError apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "Wrong credentials");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());

        } else {
            return wrapToken(token);
        }
    }

    ResponseEntity<Object> wrapToken(String token) {
        return new ResponseEntity<>(
                token, new HttpHeaders(), HttpStatus.OK);
    }
}
