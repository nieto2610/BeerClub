package com.digitalHouse.beerClub.exceptions;

import java.util.List;

public class CompoundException extends Exception {
    private List<Exception> exceptions;

    public CompoundException(List<Exception> exceptions) {
        this.exceptions = exceptions;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }
}

