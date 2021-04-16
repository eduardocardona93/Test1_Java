package com.example.test1_java;

public class Course {
    private String name;
    private double fees;
    private int hours;

    public Course(String name, double fees, int hours) {
        this.name = name;
        this.fees = fees;
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public double getFees() {
        return fees;
    }

    public int getHours() {
        return hours;
    }
}
