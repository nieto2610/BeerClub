package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;
    @NotBlank
    private String name;

    @Size(max = 255, message = "Description must have at most 255 characters")
    private String description;

    @NotNull
    @Positive(message = "Price must be greater than 0")
    private Double price;
    private List<@Valid BenefitDTO> benefits;

    @NotNull(message = "isRecommended must not be null")
    @AssertTrue(message = "isRecommended must be true or false")
    private Boolean isRecommended;

    @AssertTrue(message = "isActive must be true or false")
    private Boolean isActive;
}
