package com.claudiusava.diningReviews.controller;
import com.claudiusava.diningReviews.Check;
import com.claudiusava.diningReviews.model.Review;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.ReviewRepository;
import com.claudiusava.diningReviews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;


    @GetMapping("/")
    private String showAdminPage(Model model,
                                 @CookieValue("isAdmin") String isAdminString,
                                 @RequestParam Optional<String> sortBy){

        if(Check.isAdmin(isAdminString)) {

            if(sortBy.isPresent()){
                List<Review> reviewList = reviewRepository.findAllBy(Sort.by(sortBy.get()));
                model.addAttribute("reviewList", reviewList);
            } else {
                Iterable<Review> reviewList = reviewRepository.findAll();
                model.addAttribute("reviewList", reviewList);
            }
            model.addAttribute("title", "Admin Page");
            return "admin";

        }

        model.addAttribute("error", "You are not an admin. Go away or I will call your mama");
        return ErrorController.error(model);

    }

    @GetMapping("/new")
    private String showNewUserPage(Model model){
        model.addAttribute("title", "New Admin");
        return "newAdmin";
    }


    @PostMapping("/new")
    private String addNewAdmin(@ModelAttribute User user,
                               @CookieValue(value = "isAdmin", defaultValue = "false") String isAdminString,
                               Model model){

        boolean isAdmin = Boolean.parseBoolean(isAdminString);

        if(!Check.usernameExists(user.getUsername(), userRepository) && isAdmin){
            user.setIsAdmin(true);
            userRepository.save(user);
            model.addAttribute("title", "New Admin");
            return "redirect:/admin/";
        }
        model.addAttribute("error", "Admin creation error.");
        return ErrorController.error(model);
    }

    @GetMapping("/accept")
    private String showAcceptPage(@RequestParam Long id,
                                  Model model,
                                  @CookieValue("isAdmin") String isAdminString){

        if(Check.isAdmin(isAdminString)){
            Review review = reviewRepository.findById(id).get();
            model.addAttribute("review", review);
            model.addAttribute("title", "Accept Review");
            return "acceptReview";
        }
        model.addAttribute("error", "You are not an admin. Go away or I will call your mama");
        return ErrorController.error(model);
    }


    @PostMapping("/accept")
    private String acceptReviews(@ModelAttribute("id") String id,
                                 @ModelAttribute("isAccepted") String isAcceptedString,
                                 @CookieValue(value = "isAdmin", defaultValue = "false") String isAdminString,
                                 Model model){

        if(Check.isAdmin(isAdminString)){
            Review review = reviewRepository.findById(Long.parseLong(id)).get();

            Boolean isAccepted = Boolean.parseBoolean(isAcceptedString);
            if(isAccepted) {
                review.setIsAccepted(isAccepted);
            }
            reviewRepository.save(review);
            return "redirect:/admin/";
        }
        model.addAttribute("error", "You are not an admin. Go away or I will call your mama");
        return ErrorController.error(model);
    }


}
