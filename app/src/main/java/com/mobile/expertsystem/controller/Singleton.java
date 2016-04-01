package com.mobile.expertsystem.controller;

import android.app.Application;

import com.mobile.expertsystem.model.Aturan;
import com.mobile.expertsystem.model.Gejala;
import com.mobile.expertsystem.model.Organ;
import com.mobile.expertsystem.model.Penyakit;

import java.util.ArrayList;

public class Singleton extends Application {

    Organ selectedOrgan;
    ArrayList <Gejala> listGejala = new ArrayList<>();
    ArrayList <Aturan> listAturan  = new ArrayList<>();
    ArrayList <Penyakit> listPenyakit = new ArrayList<>();

    private static Singleton publicVar;

    private Singleton(){

    }

    public static Singleton getInstance(){
        if(publicVar == null){
            publicVar = new Singleton();
        }

        return publicVar;
    }

    public Organ getSelectedOrgan() {
        return selectedOrgan;
    }

    public void setSelectedOrgan(Organ selectedOrgan) {
        this.selectedOrgan = selectedOrgan;
    }

    public ArrayList<Gejala> getListGejala() {
        return listGejala;
    }

    public void setListGejala(ArrayList<Gejala> listGejala) {
        this.listGejala = listGejala;
    }

    public void addGejala(Gejala gejala){
        listGejala.add(gejala);
    }

    public void clearGejala(){
        listGejala.clear();
    }

    public ArrayList<Aturan> getListAturan() {
        return listAturan;
    }

    public void setListAturan(ArrayList<Aturan> listAturan) {
        this.listAturan = listAturan;
    }

    public ArrayList<Penyakit> getListPenyakit() {
        return listPenyakit;
    }

    public void setListPenyakit(ArrayList<Penyakit> listPenyakit) {
        this.listPenyakit = listPenyakit;
    }
}
