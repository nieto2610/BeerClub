package com.digitalHouse.beerClub.exceptions;

public class EmailExistsException extends Exception{
    public EmailExistsException(String mensaje) {
        super(mensaje);
    }
}
