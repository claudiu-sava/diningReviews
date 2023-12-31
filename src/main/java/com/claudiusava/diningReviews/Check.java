package com.claudiusava.diningReviews;

import com.claudiusava.diningReviews.model.Review;
import com.claudiusava.diningReviews.model.Role;
import com.claudiusava.diningReviews.model.User;
import com.claudiusava.diningReviews.repository.ReviewRepository;
import com.claudiusava.diningReviews.repository.UserRepository;
import com.claudiusava.diningReviews.services.UserDetail;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.CookieValue;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Check {

    public static Boolean isInputOk(Review review){
        if(review.getDiaryReview() > 6 || review.getDiaryReview() < 1){
            return false;
        } else if (review.getEggReview() > 6 || review.getEggReview() < 1){
            return false;
        } else if (review.getPeanutReview() > 6 || review.getPeanutReview() < 1){
            return false;
        }
        return true;
    }


    public static Boolean usernameExists(String username,
                                         UserRepository userRepository){

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()){
            return true;
        }

        return false;
    }

    public static Boolean userAlreadyReviewedThisRestaurant(Review review,
                                                            String username,
                                                            ReviewRepository reviewRepository){

        List<Review> reviewList = reviewRepository.findAllByReviewedBy(username);
        for (Review reviewFromList : reviewList){
            if(review.getName().equals(reviewFromList.getName())){
                return true;
            }
        }

        return false;
    }

    public static Boolean isAdmin(String xx){
        return false;
    }

    public static Boolean isAdmin2(){

        for(GrantedAuthority role : UserDetail.getLoggedUserRole()){
            System.out.println(role.getAuthority());
            if(role.getAuthority().contains("ADMIN")){
                return true;
            }
        }
        return false;
    }

}
