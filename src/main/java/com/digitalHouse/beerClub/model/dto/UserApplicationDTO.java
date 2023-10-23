package com.digitalHouse.beerClub.model.dto;

import java.time.LocalDate;

public class UserApplicationDTO {
    private String firstName, lastName, email;
    private LocalDate birthday;
    private String telephone, country, province, city, street;
    private int number, floor;
    private String apartment, zipCode, password;

    public UserApplicationDTO() {};

    public UserApplicationDTO(String firstName, String lastName, String email, LocalDate birthday, String telephone, String country, String province, String city, String street, int number, int floor, String apartment, String zipCode, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.telephone = telephone;
        this.country = country;
        this.province = province;
        this.city = city;
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.apartment = apartment;
        this.zipCode = zipCode;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() { return birthday; }

    public String getTelephone() {
        return telephone;
    }

    public String getCountry() {
        return country;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public int getFloor() {
        return floor;
    }

    public String getApartment() {
        return apartment;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserApplicationDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", telephone='" + telephone + '\'' +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", floor=" + floor +
                ", apartment='" + apartment + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
