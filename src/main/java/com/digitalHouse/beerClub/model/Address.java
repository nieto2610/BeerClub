package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    private String province;
    private String city;
    private String street;
    private int number;
    private int floor;
    private String apartment;
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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
