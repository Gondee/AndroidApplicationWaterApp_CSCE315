package com.KRUGER.IsPureWater;

import android.util.Log;

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
    public Boolean isOverHealthLimit;
    public Boolean isOverLegalLimit;

    Contaminant(String n, String a, String m, String hl, String ll) {
        name = n;
        units = a.replaceAll("[\\d+\\.?\\d*]","");
        average = a.replaceAll("[^\\d+\\.?\\d*\\s+]","");
        max = m;
        health_limit = (noValue(hl)) ? "No health limit" : hl;
        legal_limit = (noValue(ll)) ? "No legal limit" : ll;
        isOverHealthLimit = (noValue(hl)) ? false : (Double.parseDouble(max) > Double.parseDouble(health_limit));
        isOverLegalLimit = (noValue(ll)) ? false : (Double.parseDouble(max) > Double.parseDouble(legal_limit));
    }

    private Boolean noValue(String v) {
        if(v.equals("-"))
            return true;
        else
            return false;
    }
}
