package com.mobile.expertsystem.model;

/**
 * Created by billysusanto on 3/27/2016.
 */
public class Organ {
    String name;
    int id;

    public Organ(){}

    public Organ(String name, int id){
        this.name = name;
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setId(int Id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
