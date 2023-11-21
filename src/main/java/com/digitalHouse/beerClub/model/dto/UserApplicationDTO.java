package com.digitalHouse.beerClub.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserApplicationDTO {

    @NotBlank(message = "El nombre es requerido")
    @Size(min=2, max = 10, message = "El nombre debe tener entre 2 y 10 caracteres")
    private String name;

    @NotBlank(message = "El apellido es requerido")
    @Size(min=2, max = 20, message = "El apellido debe tener entre 2 y 20 caracteres")
    private String lastName;

    @NotBlank(message = " El correo electrónico es requerido")
    @Email(message = "El correo electrónico debe ser válido")
    private String email;

    @Column(nullable = false)
    @Past
    private LocalDate birthdate;

    @NotBlank(message = "El número de teléfono es requerido")
    private String telephone;

    @NotBlank(message = "El país es requerido")
    @Size(min=2, message = "El nombre del país debe contener dos o más caracteres")
    private String country;

    @NotBlank(message = "La provincia es requerida")
    @Size(min=2, message = "El nombre de la provincia debe contener dos o más caracteres")
    private String province;

    @NotBlank(message = "La ciudad es requerida")
    @Size(min=2, message = "El nombre de la ciudad debe contener dos o más caracteres")
    private String city;

    @NotBlank(message = "La calle es requerida")
    private String street;

    private String number, floor;

    private String apartment;

    @NotBlank(message = "El código postal es requerido")
    private String zipCode;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*?.])(?=\\S+$).{8,16}$",
            message = "La contraseña debe contener entre 8 y 16 caracteres y cumplir con los siguientes criterios: al menos una letra mayúscula, una letra minúscula, un número y un caracter especial @#$%^&+=!)."
    )
    private String password;

    private Long subscriptionId;

    private String cardHolder;

    @NotNull(message = "El número de la tarjeta es requerido")
    private String cardNumber;

    @NotNull(message = "El cvv de la tarjeta es requerido")
    private String cvv;

    @Column(nullable = false, updatable = false)
    private String expDate;

}
