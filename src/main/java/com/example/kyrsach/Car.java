package com.example.kyrsach;

public class Car {

    String Brand;
    String Model;
    double Cost;


    public Car() {
    }

    public Car(String brand, String model, double cost) {
        Brand = brand;
        Model = model;
        Cost = cost;
    }

    public String getBrand() {
        return Brand;
    }

    public void setBrand(String brand) {
        Brand = brand;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }

    @Override
    public String toString() {
        return Brand + " " + Model + " " + Cost;
    }
}