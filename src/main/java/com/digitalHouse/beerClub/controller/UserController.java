package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> userDTOS = userService.getUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/active")
    public ResponseEntity<List<UserDTO>> getAllActiveUsers() {
        List<UserDTO> userDTOS = userService.getAllActiveUsers();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) throws ResourceNotFoundException{
       try {
            UserDTO userDTO = userService.getUserById(id);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
       } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }
    }

    @PostMapping()
    public ResponseEntity<Object> saveUser(@RequestBody UserApplicationDTO user) {
        try{
            UserDTO userDTO = userService.saveUser(user);
            return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
        }catch (EmailExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }catch (CompoundException e) {
            List<Exception> exceptions = e.getExceptions();
            List<String> errorMessages = new ArrayList<>();
            for (Exception ex : exceptions) {
                if (ex instanceof InvalidPasswordException || ex instanceof MissingFieldsException) {
                    errorMessages.add(ex.getMessage());
                }
            }
            if (!errorMessages.isEmpty()) {
                return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/email")
    public ResponseEntity<UserDTO> getUser(@RequestBody String email) {
        UserDTO userDTO = userService.getUserAuth(email);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody UserApplicationDTO user) throws CompoundException {
        try {
            UserDTO userDTO = userService.UpdateUser(user);
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }catch (CompoundException e) {
            List<Exception> exceptions = e.getExceptions();
            List<String> errorMessages = new ArrayList<>();
            for (Exception ex : exceptions) {
                if (ex instanceof InvalidPasswordException || ex instanceof MissingFieldsException) {
                    errorMessages.add(ex.getMessage());
                }
            }
            if (!errorMessages.isEmpty()) {
                return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<String> updatePasswordUser(@RequestBody UserApplicationDTO user) throws MissingFieldsException, InvalidPasswordException {
        try {
            userService.UpdatePasswordUser(user);
            return new ResponseEntity<>("Password successfully updated.", HttpStatus.OK);
        }catch (MissingFieldsException | InvalidPasswordException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/activate/{userId}")
    public ResponseEntity<String> activateUserSubscription(@PathVariable Long userId) {
        try {
            userService.activateUserSubscription(userId);
            return new ResponseEntity<>("User subscription activated successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> softDeleteUser(@PathVariable Long userId) {
        try {
            userService.softDeleteUser(userId);
            return new ResponseEntity<>("User soft-deleted successfully", HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
