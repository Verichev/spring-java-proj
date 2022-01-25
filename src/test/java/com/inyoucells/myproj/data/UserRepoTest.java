package com.inyoucells.myproj.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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

        String token = userRepo.addUser("email1", "pass1");

        Mockito.verify(userRepo).createToken(1);
        assertEquals("token1", token);

        token = userRepo.addUser("email2", "pass1");

        Mockito.verify(userRepo).createToken(2);
        assertEquals("token2", token);

        token = userRepo.addUser("email1", "pass2");
        Mockito.verify(userRepo, Mockito.times(2)).createToken(anyLong());
        assertNull(token);
    }

    @Test
    void checkUser() {
        Mockito.doReturn("token1").when(userRepo).createToken(1);

        String token = userRepo.checkUser("email1", "pass1");

        assertNull(token);

        userRepo.addUser("email1", "pass1");

        token = userRepo.checkUser("email1", "pass1");

        Mockito.verify(userRepo, Mockito.times(2)).createToken(1);
        assertEquals("token1", token);

        token = userRepo.checkUser("email2", "pass1");

        Mockito.verify(userRepo, Mockito.times(2)).createToken(anyLong());
        assertNull(token);
    }
}