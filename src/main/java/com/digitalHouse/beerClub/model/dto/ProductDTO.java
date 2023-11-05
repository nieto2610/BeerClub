package com.digitalHouse.beerClub.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @JsonIgnore
    private Long id;
    private String name;
    private String description;
    @JsonProperty("image_url")
    private List<ProductImageDTO> imageUrl;
    @JsonIgnore
    private List<RecommendationDTO> recommendationList;
}
