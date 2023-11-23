package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.AccountResponseDTO;
import com.digitalHouse.beerClub.model.dto.CardAppDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;
import com.digitalHouse.beerClub.model.dto.CardResponseDTO;
import com.digitalHouse.beerClub.service.interfaces.ICardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Cards")
@RequestMapping("/cards")
@Validated
public class CardController {

    private final ICardService cardService;

    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<CardDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.searchAll());
    }

    @PostMapping
    public ResponseEntity<CardDTO> save(@RequestBody @Valid CardAppDTO cardAppDTO) throws BadRequestException, NotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(cardAppDTO));
    }

    @PatchMapping("/{id}/debit")
    public ResponseEntity<Object> processDebitOperation(
            @PathVariable @Positive(message = "Id must be greater than 0") Long id,
            @RequestParam @Positive(message = "Amount must be greater than 0") Double amount
    ) throws NotFoundException, InsufficientBalanceException {
       try {
           return ResponseEntity.status(HttpStatus.OK).body(cardService.cardDebit(id, amount));
       }catch (NotFoundException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
       }catch (InsufficientBalanceException e) {
           return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }

    @PatchMapping("/{id}/credit")
    public ResponseEntity<Object> processCreditOperation(
            @PathVariable @Positive(message = "Id must be greater than 0") Long id,
            @RequestParam @Positive(message = "Amount must be greater than 0") Double amount
    ) throws NotFoundException {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(cardService.cardCredit(id, amount));
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDTO> getById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardDTO> update(@RequestBody @Valid CardDTO cardDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.update(cardDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        cardService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<CardDTO> activateCard(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(cardService.activeCard(id));
    }

    @GetMapping("/cardNumber/{cardNumber}")
    public ResponseEntity<CardDTO> getByCardNumber(@PathVariable String cardNumber) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(cardService.searchByCardNumber(cardNumber));
    }
}
