package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Account;
import com.digitalHouse.beerClub.model.dto.AccountAppDTO;
import com.digitalHouse.beerClub.model.dto.AccountDTO;
import com.digitalHouse.beerClub.model.dto.AccountResponseDTO;
import com.digitalHouse.beerClub.model.dto.CardType;
import com.digitalHouse.beerClub.repository.IAccountRepository;
import com.digitalHouse.beerClub.service.interfaces.IAccountService;
import com.digitalHouse.beerClub.utils.AccountUtils;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    private final IAccountRepository accountRepository;
    private final Mapper mapper;

    public AccountService(IAccountRepository accountRepository, Mapper mapper) {
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }


    @Override
    public List<AccountDTO> searchAll() {
        return accountRepository.findAll()
                .stream().map(s -> mapper.converter(s, AccountDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AccountDTO searchById(Long id) throws NotFoundException {
        return accountRepository.findById(id)
                .stream().map(s -> mapper.converter(s, AccountDTO.class)).findFirst()
                .orElseThrow(() -> new NotFoundException("Account not found"));
    }

    @Override
    public AccountDTO create(AccountDTO accountDTO) throws BadRequestException {
        accountDTO.setIsActive(true);
        Account newAccount = accountRepository.save(mapper.converter(accountDTO, Account.class));
        return mapper.converter(newAccount, AccountDTO.class);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO, Long id) throws NotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!account.getIsActive()) {
            throw new NotFoundException("The Account is not active and cannot be modified.");
        }
        account.setAccountHolder(accountDTO.getAccountHolder());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setBalance(accountDTO.getBalance());
        Account accountUpdated = accountRepository.save(account);
        return mapper.converter(accountUpdated, AccountDTO.class);
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Account account = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!account.getIsActive()) {
            throw new NotFoundException("The Account is not active and cannot be deleted.");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO createAccount(AccountAppDTO accountAppDTO) throws BadRequestException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountHolder(accountAppDTO.getAccountHolder());
        accountDTO.setAccountNumber(AccountUtils.getAccountNumber());
        accountDTO.setBalance(accountAppDTO.getBalance());
        accountDTO.setIsActive(true);
        Account newAccount = accountRepository.save(mapper.converter(accountDTO, Account.class));
        return mapper.converter(newAccount, AccountDTO.class);
    }

    @Override
    public AccountResponseDTO debit(Long accountId, Double amount) throws NotFoundException{
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        if (!account.getIsActive()) {
            throw new NotFoundException("The Account is not active and cannot be modified.");
        }
        Double balance = account.getBalance();
        if(balance<amount){
            throw new NotFoundException("The balance is lower than the amount");
        }
        balance -= amount;
        account.setBalance(balance);
        Account accountUpdated = accountRepository.save(account);

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAmount(amount);
        accountResponseDTO.setType(CardType.DEBIT);
        accountResponseDTO.setDescription("Successful");

        return accountResponseDTO;
    }

    @Override
    public AccountResponseDTO addCredit(String  accountNumber, Double amount) throws NotFoundException{
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new NotFoundException("Account not found"));

        if (!account.getIsActive()) {
            throw new NotFoundException("The Account is not active and cannot be modified.");
        }
        Double balance = account.getBalance();
        balance += amount;
        account.setBalance(balance);
        Account accountUpdated = accountRepository.save(account);

        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setAmount(amount);
        accountResponseDTO.setType(CardType.CREDIT);
        accountResponseDTO.setDescription("Successful");

        return accountResponseDTO;
    }

    @Override
    public AccountDTO activateAccount(Long accountId) throws NotFoundException {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));
        if (account.getIsActive()) {
            throw new NotFoundException("The Account is active and cannot be actived.");
        }
        account.setIsActive(true);
        accountRepository.save(account);
        return mapper.converter(account, AccountDTO.class);
    }

    public AccountDTO searchByAccountNumber(String number) throws NotFoundException {
        Account account = accountRepository.findByAccountNumber(number).orElseThrow(() -> new NotFoundException("Account not found"));
        return mapper.converter(account, AccountDTO.class);
    }
}
