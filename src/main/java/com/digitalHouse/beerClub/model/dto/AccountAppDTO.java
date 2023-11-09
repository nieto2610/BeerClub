package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountAppDTO {
    private Long id;
    @NotBlank(message = "Account accountHolder must not be blank")
    private String accountHolder;
    @NotNull(message = "Account balance must not be null")
    @Positive(message = "Account balance must be greater or equal than 0")
    private Double balance;
}
