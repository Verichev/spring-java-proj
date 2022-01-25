package com.inyoucells.myproj.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class UserRepoTest {

    private UserRepo userRepo;

    @BeforeEach
    void setup() {
        userRepo = Mockito.spy(new UserRepo());
    }

    @Test
    void getUsers() {
        Mockito.doReturn("token1").when(userRepo).createToken(1);
        Mockito.doReturn("token2").when(userRepo).createToken(2);

        Optional<String> token = userRepo.addUser("email1", "pass1");

        Mockito.verify(userRepo).createToken(1);
        assertTrue(token.isPresent());
        assertEquals("token1", token.get());

        token = userRepo.addUser("email2", "pass1");

        Mockito.verify(userRepo).createToken(2);
        assertTrue(token.isPresent());
        assertEquals("token2", token.get());

        token = userRepo.addUser("email1", "pass2");
        Mockito.verify(userRepo, Mockito.times(2)).createToken(anyLong());
        assertTrue(token.isEmpty());
    }

    @Test
    void checkUser_whenUsersEmpty() {
        Optional<String> token = userRepo.checkUser("email1", "pass1");

        assertTrue(token.isEmpty());
    }

    @Test
    void checkUser_whenUserPresent() {
        Mockito.doReturn("token1").when(userRepo).createToken(1);
        userRepo.addUser("email1", "pass1");

        Optional<String> token = userRepo.checkUser("email1", "pass1");

        Mockito.verify(userRepo, Mockito.times(2)).createToken(1);
        assertTrue(token.isPresent());
        assertEquals("token1", token.get());

        token = userRepo.checkUser("email2", "pass1");

        Mockito.verify(userRepo, Mockito.times(2)).createToken(anyLong());
        assertTrue(token.isEmpty());
    }
}