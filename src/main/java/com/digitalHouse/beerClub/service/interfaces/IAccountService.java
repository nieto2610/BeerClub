package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.AccountAppDTO;
import com.digitalHouse.beerClub.model.dto.AccountDTO;

public interface IAccountService extends IService<AccountDTO>{
    AccountDTO createAccount(AccountAppDTO accountAppDTO) throws BadRequestException;
    AccountDTO debit(Long accountId, Double amount) throws NotFoundException;
    AccountDTO credit(Long accountId, Double amount) throws NotFoundException;
    AccountDTO activateAccount(Long accountId) throws NotFoundException;
}
