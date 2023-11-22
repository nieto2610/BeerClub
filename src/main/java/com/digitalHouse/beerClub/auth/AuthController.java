package com.digitalHouse.beerClub.auth;

import com.digitalHouse.beerClub.exceptions.CustomUserAlreadyExistsException;
import com.digitalHouse.beerClub.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {

    private  final AuthService authService;

    //TODO: REALIZAR MODIFICACIONES A LAS EXCEPCIONES
    @Operation(summary = "User login", description = "Login a user and return an authentication token.")
    @ApiResponse(responseCode = "200", description = "Authenticated successfully and token returned.")
    @ApiResponse(responseCode = "401", description = "Invalid credentials.")
    @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid parameters.")
    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException | UsernameNotFoundException ex) {
            // Estas excepciones suelen lanzarse cuando las credenciales son inválidas o el usuario no se encuentra
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no registrado o credenciales inválidas.");
        }
        catch (DisabledException ex) {
            // Esta excepción se lanza cuando el usuario está desactivado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cuenta desactivada.");
        } catch (Exception ex) {
            // Para cualquier otro tipo de error, se devuelve un error 500 con un mensaje
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @Operation(summary = "User registration", description = "Register a new user and return a registration confirmation.")
    @Parameters({
            @Parameter(name = "newUser",schema = @Schema(description = "firstName",type = "string"), example = "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john.doe@example.com\",\"birthdate\":\"1990-01-01\",\"telephone\":\"123456789\",\"subscriptionDate\":\"2023-11-10\",\"password\":\"password123\",\"address\":{\"country\":\"USA\",\"province\":\"California\",\"city\":\"Los Angeles\",\"street\":\"123 Main St\",\"number\":42,\"zipCode\":\"90001\"}}")
    })
    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Bad request, missing or invalid parameters.")
    @ApiResponse(responseCode = "409", description = "Conflict, user already exists.")
    @PostMapping(value = "register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User newUser) {
        try {
            AuthResponse response = authService.register(newUser);
            return ResponseEntity.ok(response);
        } catch (CustomUserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
