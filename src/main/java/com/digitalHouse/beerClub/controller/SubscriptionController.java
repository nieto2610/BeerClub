package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exeptions.BadRequestException;
import com.digitalHouse.beerClub.exeptions.NotFoundException;
import com.digitalHouse.beerClub.exeptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.service.Implement.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.searchAll());
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid SubscriptionDTO subscriptionDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.create(subscriptionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return  ResponseEntity.status(HttpStatus.OK).body(subscriptionService.searchById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody @Valid SubscriptionDTO subscriptionDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionService.update(subscriptionDTO,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        subscriptionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
