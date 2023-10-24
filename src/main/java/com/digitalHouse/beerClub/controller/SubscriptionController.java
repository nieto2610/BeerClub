package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.service.Implement.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.searchAll());
    }

    @PostMapping
    public ResponseEntity<SubscriptionDTO> save(@RequestBody @Valid SubscriptionDTO subscriptionDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.create(subscriptionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> getById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionDTO> update(@RequestBody @Valid SubscriptionDTO subscriptionDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.update(subscriptionDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        subscriptionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
