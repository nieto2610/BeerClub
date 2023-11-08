package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Account;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
    private Long id;
    @NotBlank(message = "Card cardNumber must not be blank")
    private String cardNumber;
    @NotBlank(message = "Card cardHolderName must not be blank")
    private String cardHolderName;
    @Pattern(regexp = "^(0[1-9]|1[0-2])([0-9]{2})$", message = "Card expirationDate must be in the format MMyy")
    private LocalDate expirationDate;
    private int cvv;
    private Long accountId;
    private Boolean isActive;
}
