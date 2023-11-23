package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private String invoiceNumber;
    private Long userId;
    private PaymentStatus paymentStatus;
}
