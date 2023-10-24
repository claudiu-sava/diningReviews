package com.claudiusava.diningReviews.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.Set;

@Entity
@Table(name = "users")
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Role> roles;

    @Setter
    @Column(name = "username")
    private String username;

    @Setter
    @Column(name = "password")
    private String password;

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

}

