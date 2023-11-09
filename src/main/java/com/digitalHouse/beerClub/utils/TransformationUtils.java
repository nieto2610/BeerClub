package com.digitalHouse.beerClub.utils;

public final class TransformationUtils {

    public static int getNumber(String number) {
        return number.length() > 0 ? Integer.parseInt(number) : 0;
    }
}
