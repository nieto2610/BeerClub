package com.digitalHouse.beerClub.controller;


import com.digitalHouse.beerClub.exceptions.NoDataFoundException;
import com.digitalHouse.beerClub.model.dto.PaymentFilterDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new NoDataFoundException("Ambas fechas (inicio y fin) deben estar presentes o ninguna.");
        }
        List<UserDTO> users = service.UserGlobalData(typeSubscription, isActive, startDate, endDate);
        if (users.isEmpty()) {
            throw new NoDataFoundException("Se realizo la búsqueda, pero no se encontraron datos que coincidan con el criterio del filtro");
        } else if ((startDate != null && endDate != null)) {
            NoDataFoundException.forDateRange(startDate, endDate);
        }

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @GetMapping(value = "/payments/filterGlobalData")
    public ResponseEntity<?> getFilterPaymentsGlobalData (
            @RequestParam(required = false) String typeNameSubscription,
            @RequestParam(required = false) Double amountMin,
            @RequestParam(required = false) Double amountMax,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        if ((startDate == null && endDate != null) || (startDate != null && endDate == null)) {
            throw new NoDataFoundException("Ambas fechas (inicio y fin) deben estar presentes o ninguna.");
        }else if ((amountMin == null && amountMax != null) || (amountMin != null && amountMax == null)) {
            throw new NoDataFoundException("Ambos montos (mínimo y máximo) deben estar presentes o ninguno.");
        }
        List<PaymentFilterDTO> payments = service.getFilteredPayments(typeNameSubscription, amountMin, amountMax, startDate, endDate);
        if (payments.isEmpty()) {
            throw new NoDataFoundException("Se realizo la búsqueda, pero no se encontraron datos que coincidan con el criterio del filtro.");
        } else if ((startDate != null && endDate!= null)) {
            NoDataFoundException.forDateRange(startDate, endDate);
        } else if ((amountMax != null && amountMin != null)) {
            NoDataFoundException.validateRangeValues(amountMax, amountMin);
        }
        return ResponseEntity.status(HttpStatus.OK).body(payments);
    }

    @GetMapping(value = "/subscriptions/filterGlobalData")
    public ResponseEntity<?> getFilterSubscription(
            @RequestParam(required = false) String typeNameSubscription,
            @RequestParam(required = false) Integer isRecommended,
            @RequestParam(required = false) Integer isActive,
            @RequestParam(required = false) Double amountMin,
            @RequestParam(required = false) Double amountMax
    ) {
        if ((amountMin == null && amountMax != null) || (amountMin != null && amountMax == null)) {
            throw new NoDataFoundException("Ambos montos (mínimo y máximo) deben estar presentes o ninguno.");
        }
        List<SubscriptionDTO> SubscriptionDTO = service.getFilterSubscription(typeNameSubscription, isRecommended,isActive, amountMin, amountMax);
        if (SubscriptionDTO.isEmpty()) {
            throw new NoDataFoundException("Se realizo la búsqueda, pero no se encontraron datos que coincidan con el criterio del filtro");
        }else if ((amountMax != null && amountMin != null)) {
            NoDataFoundException.validateRangeValues(amountMax, amountMin);
        }
        return ResponseEntity.status(HttpStatus.OK).body(SubscriptionDTO);
    }


}
