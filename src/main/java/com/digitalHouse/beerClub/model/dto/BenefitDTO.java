package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDTO {

    private Long id;

    @NotBlank
    @Size(max = 255, message = "name must have at most 255 characters")
    private String name;
}
