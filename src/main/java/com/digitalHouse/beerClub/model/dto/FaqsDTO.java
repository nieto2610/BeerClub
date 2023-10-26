package com.digitalHouse.beerClub.model.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqsDTO {
    private Long id;
    private String question;
    private String answer;

    public Long getId() {
        return id;
    }
}

