package com.digitalHouse.beerClub.model.dto;



import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private @Valid Long productId;
    private @Valid Long userId;
    private Integer rating;
    private String comments;
    private Boolean existReview;
}
