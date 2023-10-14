package com.claudiusava.diningReviews.controller;

import com.claudiusava.diningReviews.Input;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginLogoutController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/logout")
    private String logout(HttpServletResponse response){

        Cookie isAdminCookie = new Cookie("isAdmin", null);
        isAdminCookie.setMaxAge(0);

        Cookie usernameCookie = new Cookie("username", null);
        usernameCookie.setMaxAge(0);

        response.addCookie(isAdminCookie);
        response.addCookie(usernameCookie);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("title", "Login");
        return "login";
    }


    @PostMapping("/login")
    private String login(HttpServletResponse response,
                         @ModelAttribute("username") String username,
                         Model model){


        Optional<User> userOptional = userRepository.findByUsername(username);
        if(!userOptional.isPresent()){
            model.addAttribute("error", "Maybe try to write you name correctly? Anyway: user not found");
            return ErrorController.error(model);
        }

        User user = userOptional.get();

        boolean isAdmin = user.getIsAdmin();

        Cookie isAdminCookie = new Cookie("isAdmin", String.valueOf(isAdmin));
        Cookie usernameCookie = new Cookie("username", username);

        response.addCookie(isAdminCookie);
        response.addCookie(usernameCookie);

        return "redirect:/";
    }

}
