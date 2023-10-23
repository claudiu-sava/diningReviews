package com.claudiusava.diningReviews.controller;

import com.claudiusava.diningReviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginLogoutController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }

}
