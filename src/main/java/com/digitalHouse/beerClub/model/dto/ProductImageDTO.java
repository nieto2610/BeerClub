package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageDTO {

    @Size(max = 255, message = "Product Image url must have at most 255 characters")
    private String url;
}
