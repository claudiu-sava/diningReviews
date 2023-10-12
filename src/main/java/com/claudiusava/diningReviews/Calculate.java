package com.claudiusava.diningReviews;

import com.claudiusava.diningReviews.model.Review;

public class Calculate {

    public static double Overall(Review review){
        return (double) (review.getDiaryReview() + review.getPeanutReview() + review.getEggReview()) / 3;
    }

}
