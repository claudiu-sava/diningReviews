package com.claudiusava.diningReviews.repository;

import com.claudiusava.diningReviews.model.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAllByIsAccepted(Boolean isAccepted, Sort sort);
    List<Review> findAllByNameAndIsAccepted(String name, Boolean isAccepted);
    List<Review> findAllByReviewedBy(String reviewedBy);
    List<Review> findAllBy(Sort sort);




}
