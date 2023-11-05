package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;

    @NotNull(message = "Transaction amount must not be null")
    @Positive(message = "Transaction amount must be greater than 0")
    private Double amount;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @NotNull(message = "Transaction paymentMethod must not be null")
    private String paymentMethod;
}
