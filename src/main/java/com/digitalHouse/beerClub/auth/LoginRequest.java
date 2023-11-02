package com.digitalHouse.beerClub.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(description = "User's email address.", example = "user@example.com", required = true)
    String userEmail;
    @Schema(description = "User's password.", example = "P@ssw0rd!", required = true)
    String password;
}
