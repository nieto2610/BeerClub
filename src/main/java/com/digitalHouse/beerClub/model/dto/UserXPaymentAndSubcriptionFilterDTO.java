package com.digitalHouse.beerClub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserXPaymentAndSubcriptionFilterDTO {
    private String name;
    private LocalDateTime subscription_date;
    private String country;
    private String status;
    private LocalDateTime lastPaymentDate;
    private Double lastPaidAmount;
    private Double totalAmount;
    private Integer is_active;
}
