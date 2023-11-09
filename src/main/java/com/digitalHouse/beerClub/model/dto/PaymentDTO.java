package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.PaymentType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentDTO {
    private Long id;
    private Double amount;
    //private PaymentType type;
    private String description;
    private LocalDateTime date;
    private String invoiceNumber;
    private Long userId;
}
