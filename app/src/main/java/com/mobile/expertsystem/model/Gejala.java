package com.mobile.expertsystem.model;

import android.app.Application;

import java.util.ArrayList;

public class Gejala extends Application {
    int id;
    String name;
    ArrayList<Detail> detail = new ArrayList<>();

    public Gejala() {
    }

    public Gejala(int id, String name, ArrayList<Detail> detail) {
        this.id = id;
        this.name = name;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Detail> getDetail() {
        return detail;
    }

    public void setDetail(ArrayList<Detail> detail) {
        this.detail = detail;
    }

    public void addDetail(int idDetail, String stringDetail){
        detail.add(new Detail(idDetail, stringDetail));
    }

}