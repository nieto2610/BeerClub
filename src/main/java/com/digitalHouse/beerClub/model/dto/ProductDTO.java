package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.Recomendation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Integer id;
    private String name;
    private String description;
    private List<ProductImageDTO> imageUrl;
    private List<RecomendationDTO> recomendationList;
}
