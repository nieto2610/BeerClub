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
    private @Valid ProductDTO product;
    private @Valid UserDTO user;
    private Integer rating;
    private String comments;

}
