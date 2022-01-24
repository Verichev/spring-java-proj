package com.inyoucells.myproj.data;

import com.inyoucells.myproj.models.User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserRepo {

    private final List<User> users = new ArrayList<>();
    private int idCounter = 0;

    public synchronized String addUser(String email, String pass) {
        boolean found = users.stream().anyMatch(it -> Objects.equals(email, it.getEmail()));
        if (found) {
            return null;
        }
        idCounter++;
        users.add(new User(idCounter, email, pass));
        return createToken(idCounter);
    }

    public synchronized String checkUser(String email, String pass) {
        Optional<User> userOptional = users.stream().filter(it -> Objects.equals(email, it.getEmail()) && Objects.equals(pass, it.getPassword())).findFirst();
        if (userOptional.isEmpty() || !pass.equals(userOptional.get().getPassword())) {
            return null;
        } else {
            return createToken(userOptional.get().getId());
        }
    }

    String createToken(long idCounter) {
        Calendar calendar = Calendar.getInstance();
        calendar.roll(Calendar.MINUTE, 5);
        String token = idCounter + "_" + calendar.getTimeInMillis();
        return token;
    }
}
