package com.digitalHouse.beerClub.exceptions;

public class InvalidPasswordException extends Exception{
    public InvalidPasswordException(String message) {
        super(message);
    }
}
