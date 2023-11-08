package com.digitalHouse.beerClub.exceptions;

public class UserActiveException extends Exception {
    public UserActiveException(String mensaje) {
        super(mensaje);
    }
}
