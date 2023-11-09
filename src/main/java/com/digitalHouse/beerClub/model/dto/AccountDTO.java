package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Account;
import com.digitalHouse.beerClub.model.Card;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    @NotBlank(message = "Account accountHolder must not be blank")
    private String accountHolder;
    @NotBlank(message = "Account accountNumber must not be blank")
    private String accountNumber;
    @NotNull(message = "Account balance must not be null")
    @Positive(message = "Account balance must be greater or equal than 0")
    private Double balance;
    private Boolean isActive;


}
