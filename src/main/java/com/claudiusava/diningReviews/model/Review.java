package com.claudiusava.diningReviews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reviews")
public class Review {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Getter @Setter
    @Column(name = "name")
    private String name;

    @Getter @Setter
    @Column(name = "overallReview")
    private double overallReview;

    @Getter @Setter
    @Column(name = "peanutReview")
    private int peanutReview;

    @Getter @Setter
    @Column(name = "eggReview")
    private int eggReview;

    @Getter @Setter
    @Column(name = "diaryReview")
    private int diaryReview;

    @Getter @Setter
    @Column(name = "reviewedBy")
    private String reviewedBy;

    @Getter @Setter
    @Column(name = "comment")
    private String comment;

    @Getter @Setter
    @Column(name = "isAccepted")
    private Boolean isAccepted = false;
}

