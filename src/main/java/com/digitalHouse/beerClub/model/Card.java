package com.digitalHouse.beerClub.model;

import com.digitalHouse.beerClub.model.dto.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;
    private String cardHolderName;
    private LocalDate expirationDate;
    private int cvv;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
    private CardType cardType;
    private Double creditLimit;
    private Boolean isActive;

    public Card(String cardNumber, String cardHolderName, LocalDate expirationDate, int cvv, Account account, CardType cardType, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.account = account;
        this.cardType = cardType;
        this.isActive = isActive;
    }

    public Card(String cardNumber, String cardHolderName, LocalDate expirationDate, int cvv, CardType cardType, Double creditLimit, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.cardType = cardType;
        this.creditLimit = creditLimit;
        this.isActive = isActive;
    }

    public Card(String cardNumber, String cardHolderName, LocalDate expirationDate, int cvv, Account account, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
        this.account = account;
        this.isActive = isActive;
    }
}
