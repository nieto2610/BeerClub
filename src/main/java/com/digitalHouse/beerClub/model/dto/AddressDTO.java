package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDTO {

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

    private String apartment;

    @NotBlank(message = "ZipCode cannot be null")
    private String zipCode;

}
