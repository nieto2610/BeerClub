package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.AgeVerification;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgeVerificationDTO {
    private Long id;
    private String ip;
    private String city;
    private LocalDate  dateOfBirth;
    private int age;

    public AgeVerificationDTO(AgeVerification ageVerification) {
        this.id = ageVerification.getId();
        this.ip = ageVerification.getIp();
        this.city = ageVerification.getCity();
        this.dateOfBirth = ageVerification.getDateOfBirth();
        this.age = ageVerification.getAge();
    }
}
