package com.digitalHouse.beerClub.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MyAppConfig {

    @Value("${myapp.accountNumber}")
    private String accountNumber;

    public String getAccountNumber() { return accountNumber; }
}
