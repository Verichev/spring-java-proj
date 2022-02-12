package com.inyoucells.myproj.service.auth.models;

import lombok.Data;

@Data
public class User {
    private final long id;
    private final String email;
    private final String password;

    public User(long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
