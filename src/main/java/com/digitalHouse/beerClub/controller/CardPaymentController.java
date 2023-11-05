package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.CardPaymentDTO;
import com.digitalHouse.beerClub.service.interfaces.ICardPaymentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "CardPayment")
@RequestMapping("/cardpayments")
@Validated
public class CardPaymentController {
    ICardPaymentService cardPaymentService;

    @GetMapping("/number")
    public ResponseEntity<Object> getCardPaymentByNumber(@RequestParam String number) throws NotFoundException {
        try {
            CardPaymentDTO cardPayment = cardPaymentService.searchByNumber(number);
            return new ResponseEntity<>(cardPayment, HttpStatus.OK);
        }catch (NotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateCardPaymentByNumber(@Valid @RequestBody CardPaymentDTO cardPaymentDTO, @RequestParam String number) throws NotFoundException {
        try {
            CardPaymentDTO cardPayment = cardPaymentService.updateByNumber(cardPaymentDTO, number);
            return new ResponseEntity<>(cardPayment, HttpStatus.OK);
        }catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
