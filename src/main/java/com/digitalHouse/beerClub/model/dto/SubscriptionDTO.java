package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Benefit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private List<Benefit> benefits;
    private Boolean isRecommended;
}
