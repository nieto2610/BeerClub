package com.digitalHouse.beerClub.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate createDate;
    @JsonProperty("subscription_id")
    private Long subscriptionId;
    @JsonProperty("image_url")
    private String imageUrl;
    private ProductDTO product;
}
