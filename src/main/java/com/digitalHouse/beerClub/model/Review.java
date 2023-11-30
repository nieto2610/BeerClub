package com.digitalHouse.beerClub.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private  User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private  Product product;

    private Integer rating;
    private String comments;
    private Boolean existReview;

}
