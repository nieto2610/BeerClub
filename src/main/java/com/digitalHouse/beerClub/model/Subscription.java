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

    //@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JoinColumn(name = "subscription_id")
    //private List<Benefit> benefits = new ArrayList<>();
    @ElementCollection
    private List<String> benefits = new ArrayList<>();

    private Boolean isRecommended;
    private Boolean isActive;

    @JsonIgnore
    @OneToMany(mappedBy = "subscription", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "subscription", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public void addUser(User user) {
        user.setSubscription(this);
        users.add(user);
    }

    public void addTransaction(Transaction transaction) {
        transaction.setSubscription(this);
        transactions.add(transaction);
    }
}
