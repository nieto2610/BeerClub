package com.digitalHouse.beerClub.controller;


import com.digitalHouse.beerClub.exceptions.ResourceNotFoundException;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping("/{userEmail}")
    public ResponseEntity<Object> getAddressByUserEmail(@PathVariable String userEmail) {
        try {
            Address address = addressService.findAddressByUserEmail(userEmail);
            return ResponseEntity.ok(address);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{userEmail}")
    public ResponseEntity<Object> updateAddressByUserEmail(@PathVariable String userEmail, @RequestBody Address updatedAddress) {
        try {
            Address updated = addressService.updateAddressByUserEmail(userEmail, updatedAddress);
            return ResponseEntity.ok(updated);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}

