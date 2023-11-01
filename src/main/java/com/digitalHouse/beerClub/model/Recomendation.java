package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recomendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private LocalDate createDate;
    private String imageUrl;
    private Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany
    @JoinTable(
            name = "recomendation_subscription",
            joinColumns = @JoinColumn(name = "recomendation_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id"))
    Set<Subscription> subscriptions;

}
