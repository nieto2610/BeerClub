package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.AgeVerificationAppDTO;
import com.digitalHouse.beerClub.model.dto.AgeVerificationDTO;
import com.digitalHouse.beerClub.service.implement.AgeVerificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/ageVerification")
public class AgeVerificationController {
    private final AgeVerificationService ageVerificationService;

    public AgeVerificationController(AgeVerificationService ageVerificationService) {
        this.ageVerificationService = ageVerificationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AgeVerificationDTO>> getAll(){
        List<AgeVerificationDTO> ageVerificationDTOS = ageVerificationService.searchAll();
        return new ResponseEntity<>(ageVerificationDTOS, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AgeVerificationDTO> save(@RequestBody @Valid AgeVerificationAppDTO dataAge) throws BadRequestException{
        AgeVerificationDTO ageVerificationDTO = ageVerificationService.create(dataAge);
        return new ResponseEntity<>(ageVerificationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgeVerificationDTO> getById(@PathVariable @Positive Long id) throws NotFoundException{
        AgeVerificationDTO ageVerificationDTO = ageVerificationService.searchById(id);
        return new ResponseEntity<>(ageVerificationDTO, HttpStatus.OK);
    }
}
