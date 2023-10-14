package com.claudiusava.diningReviews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/error")
    public static String error(Model model){
        model.addAttribute("title", "Error");
        return "error";
    }
}
