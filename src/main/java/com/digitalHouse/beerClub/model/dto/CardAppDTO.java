package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardAppDTO {
    @NotBlank(message = "Card cardHolderName must not be blank")
    private String cardHolderName;
    private Long accountId;
    private CardType cardType;
    private Double creditLimit;
}
