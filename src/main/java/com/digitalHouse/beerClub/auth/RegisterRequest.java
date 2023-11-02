package com.digitalHouse.beerClub.auth;

import com.digitalHouse.beerClub.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;

    private String lastName;

    private String email;
    private LocalDate birthdate;
    private String telephone;

    private LocalDate subscriptionDate;

    private String password;

    private Address address;
}
