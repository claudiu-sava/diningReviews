package com.claudiusava.diningReviews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "username")
    private String username;

    @Setter
    @Column(name = "city")
    private String city;

    @Setter
    @Column(name = "kanton")
    private String kanton;

    @Setter
    @Column(name = "zip")
    private Integer zip;

    @Setter
    @Column(name = "isPeanutAllergic")
    private Boolean isPeanutAllergic;

    @Setter
    @Column(name = "isEggAlergic")
    private Boolean isEggAllergic;

    @Setter
    @Column(name = "isDiaryAllergic")
    private Boolean isDiaryAllergic;

    @Setter
    @Column(name = "isAdmin")
    private Boolean isAdmin = false;

}
