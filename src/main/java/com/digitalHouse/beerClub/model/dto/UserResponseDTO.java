package com.digitalHouse.beerClub.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDTO {
    private String invoiceNumber;
    private Double amount;
    private String plan;
    private LocalDateTime invoiceDate;

}
