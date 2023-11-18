package com.digitalHouse.beerClub.controller;


import com.digitalHouse.beerClub.exceptions.NoDataFoundException;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;
import com.digitalHouse.beerClub.model.dto.PaymentFilterDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.service.implement.FilterService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Tag(name = "Filters")
@RequestMapping("/filters")
@Validated
public class FilterController {

    private FilterService service;

    public FilterController(FilterService service) {
        this.service = service;
    }

    @GetMapping(value = "/users/filterGlobalData")
    public ResponseEntity<?> getUserFilterGlobalData(
            @RequestParam(required = false) Integer typeSubscription,
            @RequestParam(required = false) Integer isActive,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOne,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTwo
    ) {
        List<UserDTO> users = service.UserGlobalData(typeSubscription, isActive, dateOne, dateTwo);
        if (users.isEmpty()) {
            throw new NoDataFoundException("Se realizo la búsqueda, pero no se encontraron datos que coincidan con el criterio del filtro");
        } else if ((dateOne != null && dateTwo != null)) {
            throw NoDataFoundException.forDateRange(dateOne, dateTwo);
        }

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping(value = "/payments/filterGlobalData")
    public ResponseEntity<?> getFilterPaymentsGlobalData(
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Double amountMin,
            @RequestParam(required = false) Double amountMax,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOne,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTwo
    ) {
        List<PaymentFilterDTO> payments = service.getFilteredPayments(descripcion, amountMin, amountMax, dateOne, dateTwo);
        if (payments.isEmpty()) {
            throw new NoDataFoundException("Se realizo la búsqueda, pero no se encontraron datos que coincidan con el criterio del filtro");
        } else if ((dateOne != null && dateTwo!= null)) {
            throw NoDataFoundException.forDateRange(dateOne, dateTwo);
        } else if ((amountMax != null && amountMin != null)) {
            throw NoDataFoundException.validateRangeValues();
        }
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }


}
