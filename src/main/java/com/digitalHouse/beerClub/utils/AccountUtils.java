package com.digitalHouse.beerClub.utils;

import java.util.Random;

public final class AccountUtils {
    public static String getAccountNumber(){
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();
        for (int i=0; i<10; i++){
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }
        return accountNumber.toString();
    }
}
