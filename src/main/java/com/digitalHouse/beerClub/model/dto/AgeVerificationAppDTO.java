package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeVerificationAppDTO {
    @NotNull
    @NotBlank(message = "ip is required")
    private String ip;
    @NotNull
    @NotBlank(message = "city is required")
    private String city;
    @NotNull(message = "dateOfBirth is required")
    private LocalDate dateOfBirth;
}
