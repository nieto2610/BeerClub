package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserApplicationDTO {

    @NotBlank(message = "Name cannot be null")
    @Size(min=3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "LastName cannot be null")
    @Size(min=2, max = 50, message = "LastName must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @NotBlank(message = "Telephone cannot be null")
    private String telephone;

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
    private String street;

    private String number, floor;

    private String apartment;

    @NotBlank(message = "ZipCode cannot be null")
    private String zipCode;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
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
