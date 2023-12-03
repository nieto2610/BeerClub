package com.digitalHouse.beerClub.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    //@JsonIgnore
    private Long id;
    @NotBlank(message = "Product name must not be blank")
    private String name;
    @Size(max = 1000, message = "Product description must have at most 1000 characters")
    private String description;
    private Float productScore;
    @JsonProperty("image_url")
    private List<@Valid ProductImageDTO> imageUrl;
    @JsonIgnore
    private List<RecommendationDTO> recommendationList;
    @JsonIgnore
    private List<ReviewDTO> reviewList;
}
