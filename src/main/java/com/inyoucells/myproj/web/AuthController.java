package com.inyoucells.myproj.web;

import com.inyoucells.myproj.models.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class AuthController {
    int id = 0;
    Map<String, String> users = new HashMap<>();
    Set<String> tokens = new HashSet<>();

    @GetMapping(path = "/signup")
    ResponseEntity<Object> signup(String email, String pass) {
        if (users.containsKey(email)) {
            ApiError apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "The email is already taken");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        } else {
            return createAndAddToken(email, pass);
        }
    }

    @GetMapping(path = "/authorize")
    ResponseEntity<Object> signin(String email, String pass) {
        if (!pass.equals(users.get(email))) {
            ApiError apiError =
                    new ApiError(HttpStatus.BAD_REQUEST, "Wrong credentials");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());

        } else {
            return createAndAddToken(email, pass);
        }
    }

    @GetMapping(path = "/request")
    ResponseEntity<Object> authorizedRequest(String token) {
        if (!tokens.contains(token)) {
            ApiError apiError =
                    new ApiError(HttpStatus.UNAUTHORIZED, "Unauthorised request");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());
        }
        String[] sp = token.split("_");
        Calendar calendar = Calendar.getInstance();
        Long expired = null;

        try {
            expired = Long.parseLong(sp[1]);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (expired != null && calendar.getTimeInMillis() > expired) {
            ApiError apiError =
                    new ApiError(HttpStatus.UNAUTHORIZED, "Authorization is outdated");
            return new ResponseEntity<>(
                    apiError, new HttpHeaders(), apiError.getStatus());

        } else {
            return new ResponseEntity<>(
                    "Authorized Response", new HttpHeaders(), HttpStatus.OK);
        }
    }

    ResponseEntity<Object> createAndAddToken(String email, String pass) {
        users.put(email, pass);
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.MINUTE, 1);
        id++;
        String token = id + "_" + calendar.getTimeInMillis();
        tokens.add(token);
        return new ResponseEntity<>(
                token, new HttpHeaders(), HttpStatus.OK);
    }

}
