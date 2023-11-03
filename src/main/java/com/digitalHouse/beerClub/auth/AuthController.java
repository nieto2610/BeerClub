package com.digitalHouse.beerClub.auth;

import com.digitalHouse.beerClub.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private  final AuthService authService;
    @Operation(summary = "User login", description = "Login a user and return an authentication token.")
    @ApiResponse(responseCode = "200", description = "Authenticated successfully and token returned.")
    @ApiResponse(responseCode = "401", description = "Invalid credentials.")
    @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid parameters.")
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserAuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "User registration", description = "Register a new user and return a registration confirmation.")
    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid parameters.")
    @ApiResponse(responseCode = "409", description = "Conflict, user already exists.")
    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody User request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
