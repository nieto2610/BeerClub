package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Country cannot be null")
    @Size(min=4)
    private String country;

    @NotBlank(message = "Province cannot be null")
    @Size(min=4)
    private String province;

    @NotBlank(message = "City cannot be null")
    @Size(min=4)
    private String city;

    @NotBlank(message = "Street cannot be null")
    @Size(min=4)
    private String street;

    private int number;
    private int floor;

    @NotBlank(message = "Apartment cannot be null")
    private String apartment;

    @NotBlank(message = "ZipCode cannot be null")
    private String zipCode;

    public Address(String country, String province, String city, String street, int number, int floor, String apartment, String zipCode) {
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.apartment = apartment;
        this.zipCode = zipCode;
    }

}
