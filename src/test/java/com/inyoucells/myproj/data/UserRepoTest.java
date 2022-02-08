package com.inyoucells.myproj.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.inyoucells.myproj.data.entity.UserEntity;
import com.inyoucells.myproj.data.jpa.UserJpaRepository;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRepoTest {

    @Mock
    UserJpaRepository userJpaRepository;

    private UserRepo userRepo;

    @BeforeEach
    void setup() {
        userRepo = Mockito.spy(new UserRepo(userJpaRepository));
    }

    @Test
    void addUser_isPresent() {
        UserEntity userEntity = new UserEntity("email", "pass");
        userEntity.setId(4L);
        Mockito.doReturn(Optional.of(userEntity)).when(userJpaRepository).findByEmail("email");

        ServiceError serviceError = assertThrows(ServiceError.class, () -> userRepo.addUser("email", "pass"));
        assertEquals(new ServiceError(TypicalError.EMAIL_IS_ALREADY_TAKEN), serviceError);
    }

    @Test
    void addUser() {
        Mockito.doReturn("token1").when(userRepo).createToken(4);
        UserEntity savedUserEntity = new UserEntity("email", "pass");
        UserEntity userEntity = new UserEntity("email", "pass");
        savedUserEntity.setId(4L);
        Mockito.doReturn(Optional.empty()).when(userJpaRepository).findByEmail("email");
        Mockito.doReturn(savedUserEntity).when(userJpaRepository).save(userEntity);

        String token = userRepo.addUser("email", "pass");
        assertEquals("token1", token);
    }

    @Test
    void checkUser_notPresent() {
        Mockito.doReturn(Optional.empty()).when(userJpaRepository).findByEmailAndPassword("email", "pass");

        ServiceError serviceError = assertThrows(ServiceError.class, () -> userRepo.loginUser("email", "pass"));
        assertEquals(new ServiceError(TypicalError.WRONG_CREDENTIALS), serviceError);
    }

    @Test
    void loginUser() {
        Mockito.doReturn("token1").when(userRepo).createToken(4);
        UserEntity userEntity = new UserEntity("email", "pass");
        userEntity.setId(4L);
        Mockito.doReturn(Optional.of(userEntity)).when(userJpaRepository).findByEmailAndPassword("email", "pass");

        String token = userRepo.loginUser("email", "pass");
        assertEquals("token1", token);
    }

    @Test
    void removeUser() {
        userRepo.removeUser(10);
        Mockito.verify(userJpaRepository).deleteById(10L);
    }
}