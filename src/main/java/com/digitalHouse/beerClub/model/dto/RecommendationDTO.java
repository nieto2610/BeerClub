package com.digitalHouse.beerClub.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationDTO {

    private Long id;
    @NotBlank(message = "Recommendation name must not be blank")
    private String title;
    @Size(max = 255, message = "Recommendation description must have at most 255 characters")
    private String description;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createDate;
    @JsonProperty("subscription_id")
    private Long subscriptionId;
    @JsonProperty("image_url")
    private String imageUrl;
    private @Valid ProductDTO product;
}
