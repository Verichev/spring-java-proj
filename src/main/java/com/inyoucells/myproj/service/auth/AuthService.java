package com.inyoucells.myproj.service.auth;

import java.util.Optional;

public interface AuthService {
    Optional<String> signup(String email, String pass);

    Optional<String> signin(String email, String pass);
}
