package com.digitalHouse.beerClub.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAdminDTO {
    @NotBlank(message = "Name cannot be null")
    @Size(min=2, max = 10, message = "Name must be between 3 and 10 characters")
    private String name;

    @NotBlank(message = "LastName cannot be null")
    @Size(min=2, max = 20, message = "LastName must be between 2 and 20 characters")
    private String lastName;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!*?.])(?=\\S+$).{8,16}$",
            message = "The password must contain at least 8 characters and meet the following criteria: at least one uppercase letter, one lowercase letter, one number, and one special character @#$%^&+=!)."
    )
    private String password;
}
