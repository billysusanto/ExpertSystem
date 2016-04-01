package com.mobile.expertsystem.model;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by billysusanto on 3/28/2016.
 */
public class Aturan extends Application {
    int sicknessId;
    ArrayList<Gejala> listGejala = new ArrayList<>();
    ArrayList<Double> listCf = new ArrayList<>();

    public Aturan(int sicknessId, ArrayList<Gejala> listGejala, ArrayList<Double> listCf) {
        this.sicknessId = sicknessId;
        this.listGejala = listGejala;
        this.listCf = listCf;
    }

    public Aturan() {

    }

    public int getSicknessId() {
        return sicknessId;
    }

    public void setSicknessId(int sicknessId) {
        this.sicknessId = sicknessId;
    }

    public ArrayList<Gejala> getListGejala() {
        return listGejala;
    }

    public void setListGejala(ArrayList<Gejala> listGejala) {
        this.listGejala = listGejala;
    }

    public void addGejala(Gejala gejala) {
        listGejala.add(gejala);
    }



    public ArrayList<Double> getListCf() {
        return listCf;
    }

    public void setListCf(ArrayList<Double> listCf) {
        this.listCf = listCf;
    }
}
