package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accountHolder;
    private String accountNumber;
    private Double balance;
    @OneToOne(mappedBy = "account")
    private Card cards;
    private Boolean isActive;

    public Account(String accountHolder, String accountNumber, Double balance, Card cards, Boolean isActive) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.cards = cards;
        this.isActive = isActive;
    }

}
