package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
public class CardPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDate expDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private  User user;

    public CardPayment(String cardHolder, String number, int cvv, LocalDate expDate) {
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.expDate = expDate;
    }
}
