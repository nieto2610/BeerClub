package com.digitalHouse.beerClub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "subscription_id")
    private Set<Benefit> benefits = new HashSet<>();

    private Boolean isRecommended;
    private Boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "subscription", fetch = FetchType.EAGER)
    private Set<Payment> payments = new HashSet<>();

    public Subscription(Long id, String name, String description, Double price, Set<Benefit> benefits, Boolean isRecommended, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.benefits = benefits;
        this.isRecommended = isRecommended;
        this.isActive = isActive;
    }

}
