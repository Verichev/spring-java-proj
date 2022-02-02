package com.inyoucells.myproj.service.auth;

import com.inyoucells.myproj.data.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Mockito.doReturn(Optional.of("test")).when(userRepo).addUser("email", "pass");
        Optional<String> result = authService.signup("email", "pass");

        Mockito.verify(userRepo).addUser("email", "pass");
        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }

    @Test
    void signin() {
        Mockito.doReturn(Optional.of("test")).when(userRepo).loginUser("email", "pass");
        Optional<String> result = authService.signin("email", "pass");

        Mockito.verify(userRepo).loginUser("email", "pass");
        assertTrue(result.isPresent());
        assertEquals("test", result.get());
    }
}