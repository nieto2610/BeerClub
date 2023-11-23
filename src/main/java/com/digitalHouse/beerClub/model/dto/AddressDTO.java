package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {

    @NotBlank(message = "Country cannot be null")
    @Size(min=4, message = "El nombre del pais debe contener cuatro o más caracteres")
    private String country;

    @NotBlank(message = "Province cannot be null")
    @Size(min=4, message = "El nombre de la provincia debe contener cuatro o más caracteres")
    private String province;

    @NotBlank(message = "City cannot be null")
    @Size(min=4, message = "El nombre de la ciudad debe contener cuatro o más caracteres")
    private String city;

    @NotBlank(message = "Street cannot be null")
    @Size(min=4, message = "El nombre de la calle debe contener cuatro o más caracteres")
    private String street;

    private int number;

    private int floor;

    private String apartment;

    @NotBlank(message = "ZipCode cannot be null")
    private String zipCode;

}
