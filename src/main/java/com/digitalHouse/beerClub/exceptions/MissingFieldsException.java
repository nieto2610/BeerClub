package com.digitalHouse.beerClub.exceptions;

public class MissingFieldsException extends Exception{
    public MissingFieldsException(String mensaje) {
        super(mensaje);
    }
}
