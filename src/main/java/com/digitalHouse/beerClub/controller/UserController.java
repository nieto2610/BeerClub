package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.dto.UserAdminDTO;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.auth.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.service.interfaces.IPaymentService;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private IPaymentService paymentService;


    @Operation(summary="List all users", description="List all users", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))})
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = IUserService.searchAll();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Operation(summary="List all active users", description="List all active users", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class))))})
    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        List<UserDTO> userDTOS = IUserService.getAllActiveUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @Operation(summary ="Find user by ID", description ="Find user by ID", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @GetMapping("/id/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException{
        UserDTO userDTO = IUserService.searchById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary="Add user", description="Add a new user", responses = {
        @ApiResponse(responseCode = "201",description = "CREATED",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<Payment> saveUser(@Valid @RequestBody UserApplicationDTO user) throws NotFoundException, EntityInactiveException, InsufficientBalanceException, BadRequestException, CustomUserAlreadyExistsException {
        paymentService.paymentValidation(user.getSubscriptionId(), user.getCardHolder(), user.getCardNumber(), user.getCvv(), user.getExpDate() );
        Payment payment = IUserService.saveUser(user);
        return new ResponseEntity<>(payment, HttpStatus.CREATED);
    }

    @Operation(summary ="Find current user", description ="Find current user",  responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getUserAuth(Authentication authentication) {
        UserDTO userDTO = IUserService.getUserAuth(authentication.getName());
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @Operation(summary="Add admin user ", description="Add a new admin user", responses = {
            @ApiResponse(responseCode = "201",description = "CREATED",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "Administrator user created successfully")))})
    @PostMapping("/create/admin")
    public ResponseEntity<?> saveAdmin(@Valid @RequestBody UserAdminDTO user){
        IUserService.saveAdmin(user);
        return new ResponseEntity<>("Admin user created successfully.", HttpStatus.CREATED);
    }

    @Operation(summary ="Find user by Email", description ="Find user by Email",  responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        UserDTO userDTO = IUserService.getUserAuth(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update a user", description = "Update an existing user", responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable @Positive(message = "Id must be greater than 0") Long id, @Valid @RequestBody UserApplicationDTO user, Authentication authentication) throws NotFoundException, ForbiddenException {
        UserDTO userDTO = IUserService.updateUser(user, id, authentication);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Operation(summary = "Update user password", description = "Update the password of an existing user",responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "Password successfully updated.")))})
    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePasswordUser( @Valid @RequestBody UserAuthRequest user, Authentication authentication) throws NotFoundException, ForbiddenException {
        IUserService.updatePasswordUser(user, authentication);
        return new ResponseEntity<>("Password successfully updated.", HttpStatus.OK);
    }

    @Operation(summary = "Activate user", description = "Activate user by ID",responses = {
        @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "User subscription activated successfully"))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @PutMapping("/activate/{userId}")
    public ResponseEntity<String> activateUser(@PathVariable @Positive(message = "Id must be greater than 0") Long userId) throws NotFoundException, UserActiveException {
        IUserService.activateUser(userId);
        return new ResponseEntity<>("User activated successfully", HttpStatus.OK);
    }

    @Operation(summary = "Delete user", description = "Delete a user by ID",responses = {
        @ApiResponse(responseCode = "204",description = "NO_CONTENT",content = @Content(mediaType = "application/json",schema = @Schema(defaultValue = "1"))),
        @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> softDeleteUser(@PathVariable @Positive(message = "Id must be greater than 0") Long userId) throws NotFoundException {
        IUserService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary ="Get top 5 by user ID", description ="Get top 5 by user ID", responses = {
            @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(type = "Array", implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "User not found with ID: 1")))})
    @GetMapping("/{userId}/top5")
    public ResponseEntity<List<ProductDTO>> getTopFiveProductsByUser(@PathVariable @Positive(message = "Id must be greater than 0") Long userId) throws NotFoundException {
        List<ProductDTO> productDTOS = IUserService.getTopFiveProducts(userId);
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @Hidden
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
