package com.inyoucells.myproj.data;

import com.inyoucells.myproj.data.entity.UserEntity;
import com.inyoucells.myproj.data.jpa.UserJpaRepository;
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

    public Optional<String> addUser(String email, String pass) {
        Optional<UserEntity> userOptional = userJpaRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return Optional.empty();
        }
        UserEntity userEntity = userJpaRepository.save(new UserEntity(email, pass));
        return Optional.of(createToken(userEntity.getId()));
    }

    public Optional<String> checkUser(String email, String pass) {
        Optional<UserEntity> userOptional = userJpaRepository.findByEmailAndPassword(email, pass);
        if (userOptional.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(createToken(userOptional.get().getId()));
        }
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
