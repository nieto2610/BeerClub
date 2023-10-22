package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "ageVerifications")
public class AgeVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotNull
    @NotBlank(message = "ip is required")
    private String ip;
    @NotNull
    @NotBlank(message = "city is required")
    private String city;
    @NotNull(message = "dateOfBirth is required")
    private LocalDate dateOfBirth;
    @NotNull(message = "age is required")
    private int age;

    public AgeVerification(String ip, String city, LocalDate dateOfBirth, int age) {
        this.ip = ip;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
    }
}
