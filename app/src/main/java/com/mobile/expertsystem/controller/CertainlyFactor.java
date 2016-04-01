package com.mobile.expertsystem.controller;

import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CertainlyFactor {
    ArrayList <Double> cf = new ArrayList<>();

    DecimalFormat df = new DecimalFormat("#.####");

    public CertainlyFactor(){
    }

    public CertainlyFactor(ArrayList <Double> cf){
        this.cf = cf;
    }

    public ArrayList <Double> getCf() {
        return cf;
    }

    public void setCf(ArrayList <Double> cf) {
        this.cf = cf;
    }

    public void clearCf(){
        this.cf.clear();
    }

    public double calculate(){
        double result = 0.0;

        for(int i=0; i<cf.size()-1; i++){
            result = cf.get(i) + ( cf.get(i+1) * (1.0 - cf.get(i)));
            cf.set(i+1, result);
        }

        Log.e("Result", (Double.parseDouble(df.format(result * 100))) + "");
        return Double.parseDouble(df.format(result * 100));
    }
}
