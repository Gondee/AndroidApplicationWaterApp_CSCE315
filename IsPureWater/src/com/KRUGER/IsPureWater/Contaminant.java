package com.KRUGER.IsPureWater;

/**
 * Created by harrison on 11/25/13.
 */
public class Contaminant {
    private String name;
    private String units;
    private String average;
    private String max;
    private String health_limit;
    private String legal_limit;
    private Boolean isOverHealthLimit;
    private Boolean isOverLegalLimit;

    Contaminant(String n, String a, String m, String hl, String ll) {
        name = n;
        units = a.replaceAll("[0-9]","");
        average = a.replaceAll("\\D+","");
        max = m;
        health_limit = (noValue(hl)) ? "No health limit" : hl;
        legal_limit = (noValue(ll)) ? "No legal limit" : hl;
        isOverHealthLimit = (noValue(hl)) ? false : (Integer.parseInt(max) > Integer.parseInt(health_limit));
        isOverLegalLimit = (noValue(ll)) ? false : (Integer.parseInt(max) > Integer.parseInt(legal_limit));
    }

    private Boolean noValue(String v) {
        if(v.equals("-"))
            return true;
        else
            return false;
    }
}
