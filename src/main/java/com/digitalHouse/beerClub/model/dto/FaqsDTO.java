package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaqsDTO {
    private Long id;
    private String question;
    @Column(columnDefinition = "TEXT")
    private String answer;

    public Long getId() {
        return id;
    }
}
