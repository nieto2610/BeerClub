package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CardPaymentDTO {
    private Long id;

    private String cardHolder;

    @NotNull(message = "Number cannot be null")
    private String number;

    @NotNull(message = "Cvv cannot be null")
    private int cvv;

    @Column(nullable = false, updatable = false)
    private LocalDate expDate;
}
