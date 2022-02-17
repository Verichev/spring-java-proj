package com.inyoucells.myproj.service.admin;

import com.inyoucells.myproj.service.auth.data.repo.UserRepo;
import com.inyoucells.myproj.service.auth.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class AdminController {

    private final UserRepo userRepo;

    public AdminController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/users")
    public String viewUsers(Model model) {
        List<User> users = userRepo.getUsers();
        model.addAttribute("users", users);
        return "view-users";
    }

    @PostMapping("/user")
    public String viewUsers(Model model, @RequestParam String email, @RequestParam String pass) {
        log.debug("Adding user: " + email + ", " + pass);
        userRepo.addUser(email, pass);
        model.addAttribute("email", email);
        return "user_added";
    }
}