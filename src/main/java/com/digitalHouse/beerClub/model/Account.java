package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Card card;
    private Boolean isActive;

    public Account(String accountHolder, String accountNumber, Double balance, Card card, Boolean isActive) {
        this.accountHolder = accountHolder;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.card = card;
        this.isActive = isActive;
    }

}
