package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {

    private Long id;
    @NotBlank(message = "Subscription name must not be blank")
    private String name;

    @Size(max = 255, message = "Subscription description must have at most 255 characters")
    private String description;

    @NotNull(message = "Subscription price must not be null")
    @Positive(message = "Subscription price must be greater than 0")
    private Double price;
    //private List<@Valid BenefitDTO> benefits = new ArrayList<>();
    private List<@Valid String> benefits = new ArrayList<>();

    @NotNull(message = "Subscription isRecommended must not be null")
    @AssertTrue(message = "Subscription isRecommended must be true or false")
    private Boolean isRecommended;

    @AssertTrue(message = "Subscription isActive must be true or false")
    private Boolean isActive;

}
