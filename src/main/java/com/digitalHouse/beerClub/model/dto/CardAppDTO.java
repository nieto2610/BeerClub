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
    @Pattern(regexp = "^(0[1-9]|1[0-2])([0-9]{2})$", message = "Card expirationDate must be in the format MMyy")
    private String expirationDate;
    private Long accountId;
    private Boolean isActive;
}
