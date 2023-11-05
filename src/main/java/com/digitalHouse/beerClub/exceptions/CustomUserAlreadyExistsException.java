package com.digitalHouse.beerClub.exceptions;

public class CustomUserAlreadyExistsException extends RuntimeException {
    public CustomUserAlreadyExistsException(String message) {
        super(message);
    }
}
