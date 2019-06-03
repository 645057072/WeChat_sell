package com.hlx.sell.utils;

public class MathUtil {


    private static final Double MONEY_RANGE=0.01;
    public static boolean equals(Double v1,Double v2){

        Double result=Math.abs(v1-v2);

        if (result<MONEY_RANGE){
            return true;
        }else {
            return false;
        }

    }
}
