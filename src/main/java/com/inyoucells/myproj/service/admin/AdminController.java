package com.inyoucells.myproj.service.admin;

import com.inyoucells.myproj.service.auth.data.repo.UserRepo;
import com.inyoucells.myproj.service.auth.models.User;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
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
}