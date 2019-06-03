package com.hlx.sell.utils;


import java.util.Random;

public class KeyUitls {

    public static String getqUniqueKey() {
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
