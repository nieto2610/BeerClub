package com.digitalHouse.beerClub.utils;

import com.digitalHouse.beerClub.exceptions.ParseDateException;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public final class CardUtils {

    private CardUtils() {}

    public static int getCVV() {
        int cvv = getRandomNumber(100, 999);
        return cvv;
    }

    public static String getCardNumber() {
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            cardNumber.append(String.format("%04d", (int) (Math.random() * 10000)));
            if (i < 3) {
                cardNumber.append("");
            }
        }
        String number = cardNumber.toString();
        return number;
    }

    public static int getRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min)) + min;
    }

    public static String getLastFourDigits(String cardNumber) {
        return cardNumber.substring(cardNumber.length() - 4);
    }

    public static LocalDate parseStringToLocalDate(String date){
        try {
            int month = Integer.parseInt(date.substring(0,2));
            int year = Integer.parseInt("20" + date.substring(2));
            int lastDayOfMonth = YearMonth.of(year, month).lengthOfMonth();

            LocalDate parsedDate = LocalDate.of(year, month, lastDayOfMonth);
            System.out.println("parseDate");
            System.out.println(parsedDate);
            return parsedDate;
        }catch (DateTimeParseException e){
            throw new ParseDateException("date is not valid: " + date);
        }
    }

    public static String parseLocalDateToString(LocalDate localDate){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMyy");
        return localDate.format(format);
    }

}
