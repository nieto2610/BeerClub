package com.digitalHouse.beerClub.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecomendationDTO {

    private Integer id;
    private String title;
    private String description;
    private LocalDate createDate;
    private String imageUrl;
    private Boolean isActive;
    private ProductDTO product;
}
