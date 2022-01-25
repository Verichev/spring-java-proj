package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.data.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepo userRepo;

    @Autowired
    public AuthServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public Optional<String> signup(String email, String pass) {
        return userRepo.addUser(email, pass);
    }

    @Override
    public Optional<String> signin(String email, String pass) {
        return userRepo.checkUser(email, pass);
    }
}
