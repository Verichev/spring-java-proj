package com.inyoucells.myproj.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.inyoucells.myproj.data.UserRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepo userRepo;
    private AuthServiceImpl authService;

    @BeforeEach
    void setup() {
        authService = new AuthServiceImpl(userRepo);
    }

    @Test
    void signup() {
        Mockito.doReturn("test").when(userRepo).addUser("email", "pass");
        String result = authService.signup("email", "pass");

        Mockito.verify(userRepo).addUser("email", "pass");
        assertEquals("test", result);
    }

    @Test
    void signin() {
        Mockito.doReturn("test").when(userRepo).loginUser("email", "pass");
        String result = authService.signin("email", "pass");

        Mockito.verify(userRepo).loginUser("email", "pass");
        assertEquals("test", result);
    }
}