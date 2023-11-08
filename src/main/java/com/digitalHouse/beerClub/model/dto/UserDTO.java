package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.RoleType;
import com.digitalHouse.beerClub.model.Subscription;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String firstName, lastName, email;
    private LocalDate birthdate;
    private String telephone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate subscriptionDate;
    private Address address;
    private RoleType role;
    private boolean active;
    private Subscription subscription;
}
