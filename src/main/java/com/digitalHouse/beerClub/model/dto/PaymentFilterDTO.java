package com.digitalHouse.beerClub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentFilterDTO {
    private Double amount;
    private String card_number;
    private LocalDateTime date;
    private String description;
    private String invoice_number;
    private String email;
    private String first_name;
    private String last_name;
}
