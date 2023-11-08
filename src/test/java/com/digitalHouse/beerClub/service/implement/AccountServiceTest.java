package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Account;
import com.digitalHouse.beerClub.model.dto.*;
import com.digitalHouse.beerClub.repository.IAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    @Mock
    private IAccountRepository accountRepository;
    @Mock
    private Mapper mapper;
    private AccountService accountService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountRepository, mapper);
    }

    @Test
    void searchAll() {
        List<Account> accounts = new ArrayList<>();
        Account account1 = new Account();
        account1.setId(1L);
        account1.setAccountHolder("Juan Duque");
        account1.setAccountNumber("1548789656");
        account1.setBalance(1000000.0);
        account1.setIsActive(true);
        Account account2 = new Account();
        account2.setId(2L);
        account2.setAccountHolder("Laura Guzman");
        account2.setAccountNumber("4875965845");
        account2.setBalance(7000000.0);
        account2.setIsActive(true);
        accounts.add(account1);
        accounts.add(account2);

        when(accountRepository.findAll()).thenReturn(accounts);
        List<AccountDTO> accountsResult = accountService.searchAll();

        assertEquals(2, accountsResult.size());
        AccountDTO firstAccount = accountsResult.get(0);
        if(firstAccount != null){
            assertEquals(1L, firstAccount.getId());
            assertEquals("Juan Duque", firstAccount.getAccountHolder());
            assertEquals("1548789656", firstAccount.getAccountNumber());
            assertEquals(1000000.0, firstAccount.getBalance());
            assertTrue(firstAccount.getIsActive());
        }

    }

    @Test
    void searchById() throws NotFoundException {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setAccountHolder("Juan Duque");
        account1.setAccountNumber("1548789656");
        account1.setBalance(1000000.0);
        account1.setIsActive(true);

        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.of(account1));
        when(mapper.converter(account1, AccountDTO.class)).thenReturn(new AccountDTO(account1.getId(), account1.getAccountHolder(), account1.getAccountNumber(),account1.getBalance(), account1.getIsActive()));

        AccountDTO accountDTO = accountService.searchById(id);

        if(accountDTO != null){
            assertEquals(1L, accountDTO.getId());
            assertEquals("Juan Duque", accountDTO.getAccountHolder());
            assertEquals("1548789656", accountDTO.getAccountNumber());
            assertEquals(1000000.0, accountDTO.getBalance());
            assertTrue(accountDTO.getIsActive());
        }
    }

    @Test
    void delete() throws ServiceException, NotFoundException {
        Account account1 = new Account();
        account1.setId(1L);
        account1.setAccountHolder("Juan Duque");
        account1.setAccountNumber("1548789656");
        account1.setBalance(1000000.0);
        account1.setIsActive(true);

        Long id = 1L;
        when(accountRepository.findById(id)).thenReturn(Optional.of(account1));
        when(accountRepository.save(account1)).thenReturn(account1);

        accountService.delete(id);
        verify(accountRepository).save(account1);
    }

    @Test
    void createAccount() throws BadRequestException {
        AccountAppDTO accountAppDTO = new AccountAppDTO();
        accountAppDTO.setAccountHolder("Juan Duque");
        accountAppDTO.setBalance(100000.0);

        Account newAccount = new Account();
        newAccount.setAccountHolder(accountAppDTO.getAccountHolder());
        newAccount.setAccountNumber("123456789");
        newAccount.setBalance(accountAppDTO.getBalance());
        newAccount.setIsActive(true);

        when(accountRepository.save(newAccount)).thenReturn(newAccount);

        AccountDTO createdAccountDTO = accountService.createAccount(accountAppDTO);

        if(createdAccountDTO != null){
            assertEquals(accountAppDTO.getAccountHolder(), createdAccountDTO.getAccountHolder());
            assertEquals("123456789", createdAccountDTO.getAccountNumber());
            assertEquals(accountAppDTO.getBalance(), createdAccountDTO.getBalance());
            assertTrue(createdAccountDTO.getIsActive());
        }
    }


    @Test
    void debit() throws NotFoundException {
        Long accountId = 1L;
        Double initialBalance = 1000.0;
        Double debitAmount = 500.0;

        Account account = new Account();
        account.setId(accountId);
        account.setBalance(initialBalance);
        account.setIsActive(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountResponseDTO response = accountService.debit(accountId, debitAmount);

        assertNotNull(response);
        assertEquals(CardType.DEBIT, response.getType());
        assertEquals("Successful", response.getDescription());
        assertEquals(initialBalance - debitAmount, account.getBalance());
    }

    @Test
    void credit() throws NotFoundException {
        Long accountId = 1L;
        Double initialBalance = 1000.0;
        Double debitAmount = 500.0;

        Account account = new Account();
        account.setId(accountId);
        account.setBalance(initialBalance);
        account.setIsActive(true);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        AccountResponseDTO response = accountService.credit(accountId, debitAmount);

        assertNotNull(response);
        assertEquals(CardType.CREDIT, response.getType());
        assertEquals("Successful", response.getDescription());
        assertEquals(initialBalance + debitAmount, account.getBalance());
    }

}