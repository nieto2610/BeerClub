package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CardPayment {
    private String cardHolder;
    private String number;
    private int cvv;
    private String expDate;


    public CardPayment(String cardHolder, String number, int cvv,String expDate) {
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.expDate = expDate;
    }
}
