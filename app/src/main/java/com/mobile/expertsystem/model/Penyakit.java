package com.mobile.expertsystem.model;

import java.util.ArrayList;

/**
 * Created by billysusanto on 3/27/2016.
 */
public class Penyakit {
    int id;
    String name;
    ArrayList<String> solution = new ArrayList<>();

    public Penyakit(){}

    public Penyakit(int id, String name, ArrayList<String> solution) {
        this.id = id;
        this.name = name;
        this.solution = solution;
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

    public ArrayList<String> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<String> solution) {
        this.solution = solution;
    }

    public void addSolution(String addSolution){
        this.solution.add(addSolution);
    }
}
