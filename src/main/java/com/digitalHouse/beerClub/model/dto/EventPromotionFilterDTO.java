package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.EventPromotionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventPromotionFilterDTO {
    private EventPromotionType type;
    private LocalDate startDateTime;
    private LocalDate endDateTime;
    private LocalDate startValidUntil;
    private LocalDate endValidUntil;
    private String location;
}
