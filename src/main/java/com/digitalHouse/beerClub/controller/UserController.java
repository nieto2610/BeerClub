package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Users")
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private IUserService IUserService;

    @Operation(summary="List all users", description="List all users", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = IUserService.searchAll();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Operation(summary="List all active users", description="List all active users", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        List<UserDTO> userDTOS = IUserService.getAllActiveUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Operation(summary ="Find user by ID", description ="Find user by ID", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @GetMapping("/id/{id}")
    public ResponseEntity<Object> getUserById(@Parameter(description = "ID del usuario a obtener", example = "1", required = true, schema = @Schema(type = "Long")) @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException{
       try {
            UserDTO userDTO = IUserService.searchById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
       } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @Operation(summary="Add user", description="Add a new user", responses = {
        @ApiResponse(responseCode = "201",description = "CREATED",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<Object> saveUser(@Valid @RequestBody UserApplicationDTO user) throws NotFoundException {
        UserDTO userDTO = IUserService.saveUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Operation(summary ="Find user by Email", description ="Find user by Email",  responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@Parameter(description = "Email del usuario a obtener", example = "example@beerClub", required = true, schema = @Schema(type = "String")) @PathVariable String email) {
        UserDTO userDTO = IUserService.getUserAuth(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update a user", description = "Update an existing user", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@Parameter(description = "ID del usuario a actualizar", example = "1", required = true, schema = @Schema(type = "Long")) @PathVariable @Positive(message = "Id must be greater than 0") Long id, @Valid @RequestBody UserApplicationDTO user) throws NotFoundException {
        try {
            UserDTO userDTO = IUserService.updateUser(user, id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update user password", description = "Update the password of an existing user",responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "Password successfully updated.")))})
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePasswordUser( @Valid @RequestBody UserAuthRequest user) throws NotFoundException {
        IUserService.updatePasswordUser(user);
        return new ResponseEntity<>("Password successfully updated.", HttpStatus.OK);
    }

    @Operation(summary = "Activate user subscription", description = "Activate the subscription of a user by ID",responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "User subscription activated successfully"))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @PutMapping("/activate/{userId}")
    public ResponseEntity<String> activateUserSubscription(@Parameter(description = "ID del usuario a activar", example = "1", required = true, schema = @Schema(type = "Long")) @PathVariable @Positive(message = "Id must be greater than 0") Long userId) {
        try {
            IUserService.activateUserSubscription(userId);
            return new ResponseEntity<>("User subscription activated successfully", HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (UserActiveException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete user", description = "Delete a user by ID",responses = {
        @ApiResponse(responseCode = "204",description = "NO_CONTENT",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "1"))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> softDeleteUser(@Parameter(description = "ID del usuario a eliminar", example = "1", required = true, schema = @Schema(type = "Long")) @PathVariable @Positive(message = "Id must be greater than 0") Long userId) {
        try {
            IUserService.delete(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }catch (ServiceException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
