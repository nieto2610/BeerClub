package com.digitalHouse.beerClub.utils;

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
                cardNumber.append(" ");
            }
        }
        String number = cardNumber.toString();
        return number;
    }

    public static int getRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min)) + min;
    }
}
