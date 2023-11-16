package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.CardAppDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;
import com.digitalHouse.beerClub.model.dto.EventPromotionDTO;
import com.digitalHouse.beerClub.service.interfaces.IEventPromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Event-Promotion")
@RequestMapping("/eventPromotion")
@Validated
public class EventPromotionController {
    @Autowired
    private final IEventPromotionService eventPromotionService;

    public EventPromotionController(IEventPromotionService eventPromotionService) {
        this.eventPromotionService = eventPromotionService;
    }

    @GetMapping
    public ResponseEntity<List<EventPromotionDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(eventPromotionService.searchAll());
    }

    @PostMapping
    public ResponseEntity<EventPromotionDTO> save(@RequestBody @Valid EventPromotionDTO eventPromotionDTO) throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventPromotionService.create(eventPromotionDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventPromotionDTO> getById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(eventPromotionService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventPromotionDTO> update(@RequestBody @Valid EventPromotionDTO eventPromotionDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(eventPromotionService.update(eventPromotionDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        eventPromotionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
