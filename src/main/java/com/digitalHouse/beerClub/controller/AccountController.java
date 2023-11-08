package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.AccountAppDTO;
import com.digitalHouse.beerClub.model.dto.AccountDTO;
import com.digitalHouse.beerClub.model.dto.AccountResponseDTO;
import com.digitalHouse.beerClub.service.interfaces.IAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Accounts")
@RequestMapping("/api/v1/accounts")
@Validated
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.searchAll());
    }

    @PostMapping
    public ResponseEntity<AccountDTO> save(@RequestBody @Valid AccountAppDTO accountAppDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(accountAppDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getById(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountDTO> update(@RequestBody @Valid AccountDTO accountDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.update(accountDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        accountService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/debit")
    public ResponseEntity<AccountResponseDTO> debit(
            @PathVariable @Positive(message = "Id must be greater than 0") Long id,
            @RequestParam @Positive(message = "Amount must be greater than 0") Double amount
    ) throws NotFoundException, Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.debit(id, amount));
    }

    @PostMapping("/{id}/credit")
    public ResponseEntity<AccountResponseDTO> credit(
            @PathVariable @Positive(message = "Id must be greater than 0") Long id,
            @RequestParam @Positive(message = "Amount must be greater than 0") Double amount
    ) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.credit(id, amount));
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<AccountDTO> activateAccount(@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(accountService.activateAccount(id));
    }
}
