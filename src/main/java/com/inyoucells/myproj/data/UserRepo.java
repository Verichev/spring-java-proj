package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.UserEntity;
import com.inyoucells.myproj.data.jpa.UserJpaRepository;
import com.inyoucells.myproj.models.errors.ServiceError;
import com.inyoucells.myproj.models.errors.TypicalError;
import com.inyoucells.myproj.service.auth.AuthConsts;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class UserRepo {

    private final UserJpaRepository userJpaRepository;

    public UserRepo(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    public String addUser(String email, String pass) {
        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            throw new ServiceError(TypicalError.EMAIL_IS_ALREADY_TAKEN);
        }
        UserEntity userEntity = userJpaRepository.save(new UserEntity(email, pass));
        return createToken(userEntity.getId());
    }

    public String loginUser(String email, String pass) {
        return createToken(userJpaRepository.findByEmailAndPassword(email, pass)
                .orElseThrow(() -> new ServiceError(TypicalError.WRONG_CREDENTIALS)).getId());
    }

    public void removeUser(long userId) {
        userJpaRepository.deleteById(userId);
    }

    String createToken(long idCounter) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, AuthConsts.TOKEN_EXPIRATION_TIME);
        String token = idCounter + "_" + calendar.getTimeInMillis();
        return token;
    }

    public void clean() {
        userJpaRepository.deleteAll();
    }
}
