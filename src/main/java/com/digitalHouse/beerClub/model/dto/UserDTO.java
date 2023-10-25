package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.RoleType;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;
    private String firstName, lastName, email;
    private LocalDate birthday;
    private String telephone;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate subscriptionDate;
    private Address address;
    private RoleType role;
    private boolean active;

}
