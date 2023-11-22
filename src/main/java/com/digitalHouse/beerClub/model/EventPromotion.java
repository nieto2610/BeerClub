package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eventPromotions")
public class EventPromotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private EventPromotionType type;
    private String title;
    private String description;
    private String imageUrl;
    private String location;
    private LocalDateTime dateTime;
    private LocalDate validUntil;
    private Boolean isActive;

    public EventPromotion(Long id, EventPromotionType type, String title, String description, String imageUrl, String location, LocalDateTime dateTime, Boolean isActive) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.dateTime = dateTime;
        this.isActive = isActive;
    }

    public EventPromotion(Long id, EventPromotionType type, String title, String description, String imageUrl, String location, LocalDate validUntil, Boolean isActive) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.location = location;
        this.validUntil = validUntil;
        this.isActive = isActive;
    }
}
