package com.claudiusava.diningReviews.controller;
import com.claudiusava.diningReviews.Calculate;
import com.claudiusava.diningReviews.Check;
import com.claudiusava.diningReviews.model.Review;
import com.claudiusava.diningReviews.model.Role;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.ReviewRepository;
import com.claudiusava.diningReviews.repository.UserRepository;
import com.claudiusava.diningReviews.services.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;



@Controller
public class ReviewsController {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping
    public String showAllAcceptedReviews(Model model,
                                         @RequestParam Optional<String> sortBy){

        if(sortBy.isPresent()){
            List<Review> reviewList = reviewRepository.findAllByIsAccepted(true, Sort.by(sortBy.get()));
            model.addAttribute("reviewList", reviewList);
        } else {
            String defaultSortBy = "id";
            List<Review> reviewList = reviewRepository.findAllByIsAccepted(true, Sort.by(defaultSortBy));
            model.addAttribute("reviewList", reviewList);
        }


        if(UserDetail.getLoggedUserUsername().equals("anonymousUser")){
            model.addAttribute("loggedInAs", "You are not logged in");
        } else {
            model.addAttribute("loggedInAs", "Logged in as " + UserDetail.getLoggedUserUsername());
        }

        model.addAttribute("username", UserDetail.getLoggedUserUsername());
        model.addAttribute("role", UserDetail.getLoggedUserRole().toString());
        model.addAttribute("title", "Dining Reviews");

        return "index";
    }


    @GetMapping("/delete/r")
    private String deleteReview(@RequestParam Long id,
                                Model model){

        boolean isAdmin = UserDetail.getLoggedUserRole().equals("ROLE_ADMIN");

        Review review = getReview(id);

        if (Objects.nonNull(review)){

            if (review.getReviewedBy().equals(UserDetail.getLoggedUserUsername()) || isAdmin){
                reviewRepository.delete(review);
                return "redirect:/";
            } else {
                model.addAttribute("error", "You cannot delete other than your reviews");
                return ErrorController.error(model);
            }

        } else {
            model.addAttribute("error", "No review with ID " + id + " found");
            return ErrorController.error(model);
        }
    }

    @GetMapping("/new")
    private String showNewReviewPage(Model model){
        model.addAttribute("title", "Add Review");
        return "review";
    }


    @PostMapping
    private String addReview(@ModelAttribute Review review,
                             Model model){

        String username = UserDetail.getLoggedUserUsername();

        // Security check hehe
        if (Check.userAlreadyReviewedThisRestaurant(review, username, reviewRepository)){
            model.addAttribute("error", "That is awkward... You already reviewed " + review.getName() + " don't you remember?");
            return ErrorController.error(model);
        } else if (username.equals("None")) {
            return "redirect:/login";
        }

        if (Check.isInputOk(review) && Check.usernameExists(username, userRepository)){
            review.setOverallReview(Calculate.Overall(review));
            review.setReviewedBy(username);
            reviewRepository.save(review);
            return "redirect:/";

        } else {
            model.addAttribute("error", "Damn, that what I call a big-o-stupidity. I might be my fault. Might. Try submitting the review again");
            return ErrorController.error(model);
        }

    }


    public Review getReview(Long id){
        Optional<Review> optionalReview = reviewRepository.findById(id);
        if(optionalReview.isPresent()){
            return optionalReview.get();
        }
        return null;
    }
}
