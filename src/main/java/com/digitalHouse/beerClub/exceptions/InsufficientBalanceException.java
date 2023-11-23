package com.digitalHouse.beerClub.exceptions;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException() {
        super("The balance is lower than the amount");
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
}
