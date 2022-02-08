package com.inyoucells.myproj.service.auth;

public interface AuthService {
    String signup(String email, String pass);

    String signin(String email, String pass);
}
