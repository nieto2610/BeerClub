package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserApplicationDTO {

    @NotBlank(message = "Name cannot be null")
    @Size(min=2, max = 10, message = "Name must be between 3 and 10 characters")
    private String name;

    @NotBlank(message = "LastName cannot be null")
    @Size(min=2, max = 20, message = "LastName must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @Past
    private LocalDate birthdate;

    @NotBlank(message = "Telephone cannot be null")
    private String telephone;

    @NotBlank(message = "Country cannot be null")
    @Size(min=2, message = "El nombre del pais debe contener cuatro o más caracteres")
    private String country;

    @NotBlank(message = "Province cannot be null")
    @Size(min=2, message = "El nombre de la provincia debe contener cuatro o más caracteres")
    private String province;

    @NotBlank(message = "City cannot be null")
    @Size(min=2, message = "El nombre de la ciudad debe contener cuatro o más caracteres")
    private String city;

    @NotBlank(message = "Street cannot be null")
    private String street;

    private String number, floor;

    private String apartment;

    @NotBlank(message = "ZipCode cannot be null")
    private String zipCode;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*?.])(?=\\S+$).{8,16}$",
        message = "The password must contain at least 8 characters and meet the following criteria: at least one uppercase letter, one lowercase letter, one number, and one special character @#$%^&+=!)."
    )
    private String password;

    private Long subscriptionId;

    private String cardHolder;

    @NotNull(message = "Number cannot be null")
    private String cardNumber;

    @NotNull(message = "Cvv cannot be null")
    private String cvv;

    @Column(nullable = false, updatable = false)
    private String expDate;

}
