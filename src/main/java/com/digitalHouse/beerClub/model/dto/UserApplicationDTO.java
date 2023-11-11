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

    @NotBlank(message = "Nombre es obligatorio")
    @Size(min=3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Apellido es obligatorio")
    @Size(min=2, max = 50, message = "LastName must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Correo electrónico es obligatorio")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @Past
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @NotBlank(message = "Telefono es obligatorio")
    private String telephone;

    @NotBlank(message = "País es obligatorio")
    @Size(min=4)
    private String country;

    @NotBlank(message = "Provincia es obligatorio")
    @Size(min=4)
    private String province;

    @NotBlank(message = "Ciudad es obligatorio")
    @Size(min=4)
    private String city;

    @NotBlank(message = "Dirección es obligatorio")
    private String street;

    private String number, floor;

    private String apartment;

    @NotBlank(message = "ZipCode es obligatorio")
    private String zipCode;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
            message = "La contraseña debe contener al menos 8 caracteres y cumplir las siguientes condiciones: al menos una mayúscula, una minúscula, un número, y un carácter especial (@#$%^&+=!)."
    )
    private String password;

    private Long subscriptionId;

    private String cardHolder;

    @NotNull(message = "Número de tarjeta es obligatorio")
    private String cardNumber;

    @NotNull(message = "Cvv es obligatorio")
    private String cvv;

    @Column(nullable = false, updatable = false)
    private String expDate;

}
