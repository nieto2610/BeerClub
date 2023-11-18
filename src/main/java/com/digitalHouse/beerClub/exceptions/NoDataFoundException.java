package com.digitalHouse.beerClub.exceptions;

import java.time.LocalDate;

public class NoDataFoundException extends RuntimeException {
    public NoDataFoundException(String message) {
        super(message);
    }
    public static NoDataFoundException forDateRange(LocalDate dateOne, LocalDate dateTwo) {
        if (dateOne.isAfter(dateTwo)) {
            return new NoDataFoundException("La fecha de inicio (" + dateOne + ") no puede ser posterior a la fecha de fin (" + dateTwo + "). Por favor, revise las fechas ingresadas.");
        } else {
            return new NoDataFoundException("No se encontró ninguna coincidencia para el rango de fechas proporcionado: desde " + dateOne + " hasta " + dateTwo + ".");
        }

    }

    public static NoDataFoundException validateRangeValues() {
        return new NoDataFoundException("Se requieren valores mínimos y máximos.");
    }
}
