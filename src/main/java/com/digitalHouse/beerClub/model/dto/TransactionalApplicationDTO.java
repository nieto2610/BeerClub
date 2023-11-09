package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransactionalApplicationDTO {
    @NotNull(message = "SubscriptionId cannot be null")
    private Long subscriptionId;

    private String cardHolder;

    @NotNull(message = "Number cannot be null")
    private String cardNumber;

    @NotNull(message = "Cvv cannot be null")
    private int cvv;

    @Column(nullable = false, updatable = false)
    private String expDate;
}
