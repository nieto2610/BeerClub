package com.digitalHouse.beerClub.exceptions;

import java.time.LocalDate;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
    public static void forDateRange(LocalDate dateOne, LocalDate dateTwo) {
        if (dateOne.isAfter(dateTwo)) {
            throw new NoDataFoundException("La fecha de inicio (" + dateOne + ") no puede ser posterior a la fecha de fin (" + dateTwo + "). Por favor, revise las fechas ingresadas.");
        }
    }

    public static void validateRangeValues(Double amountMax, Double amountMin) {
        if (amountMin != null && amountMax != null && amountMin > amountMax) {
            throw new NoDataFoundException("Es necesario que los valores mínimo y máximo estén en orden ascendente de tamaño.");
        }
    }
}
