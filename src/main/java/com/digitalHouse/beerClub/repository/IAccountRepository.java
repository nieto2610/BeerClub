package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Account;
import com.digitalHouse.beerClub.model.dto.AccountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String number);
}
