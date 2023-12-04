package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private String description;

    private LocalDateTime creationDate;

    private String cardNumber;

    private Long userId;

    private String invoiceNumber;

    private LocalDateTime invoiceDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status = PaymentStatus.PENDIENTE;

    private LocalDateTime paymentDate;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    public Payment(Double amount, String description,Long userId, String invoiceNumber, PaymentStatus status, Subscription subscription) {
        this.amount = amount;
        this.description = description;
        this.userId = userId;
        this.invoiceNumber = invoiceNumber;
        this.status = status;
        this.subscription = subscription;
    }
}
