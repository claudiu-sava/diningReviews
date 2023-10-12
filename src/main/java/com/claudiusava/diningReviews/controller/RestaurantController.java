package com.claudiusava.diningReviews.controller;

import com.claudiusava.diningReviews.model.Review;
import com.claudiusava.diningReviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/{name}")
    private List<Review> searchReviewByRestaurant(@PathVariable String name){

        List<Review> reviewList = reviewRepository.findAllByNameAndIsAccepted(name, true);

        // TODO Add overallSum on html page

        double overallSum = 0;
        for (Review review : reviewList){
            overallSum += review.getOverallReview();
        }

        return reviewList;
    }

}
