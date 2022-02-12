package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.service.auth.data.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public String signup(String email, String pass) {
        return userRepo.addUser(email, pass);
    }

    @Override
    public String signin(String email, String pass) {
        return userRepo.loginUser(email, pass);
    }
}
