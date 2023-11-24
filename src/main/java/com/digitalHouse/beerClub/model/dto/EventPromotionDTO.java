package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.EventPromotionType;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPromotionDTO {
    private Long id;
    @NotNull
    private EventPromotionType type;
    @NotNull
    @NotBlank(message = "title is required")
    private String title;
    @NotNull
    @NotBlank(message = "description is required")
    private String description;
    @NotNull
    @NotBlank(message = "imageUrl is required")
    private String imageUrl;
    @NotNull
    @NotBlank(message = "location is required")
    private String location;
    @Future
    private LocalDateTime dateTime;
    @Future
    private LocalDate validUntil;
    private Boolean isActive;
}
