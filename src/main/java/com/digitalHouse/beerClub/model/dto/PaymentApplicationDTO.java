package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentApplicationDTO {
    @NotNull(message = "PaymentId es requerido")
    private Long paymentId;

    private String cardHolder;

    @NotNull(message = "El número de la tarjeta es requerido")
    private String cardNumber;

    @NotNull(message = "El cvv de la tarjeta es requerido")
    private String cvv;

    @Column(nullable = false, updatable = false)
    private String expDate;
}
