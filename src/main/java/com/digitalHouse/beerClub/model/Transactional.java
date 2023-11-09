package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public class Transactional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Transaction amount must not be null")
    @Positive(message = "Transaction amount must be greater than 0")
    private Double amount;

   @Enumerated(EnumType.STRING)
    private TransactionalType type;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    private String cardNumber;

    private Long userId;

    private String invoiceNumber;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

}
