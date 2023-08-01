package com.andersenlab.entity;

public class ApartmentType {
    private int id;
    private int capacity;
    private String type;
    private double price;

    public ApartmentType(int id, int capacity, String type, double price) {
        this.id = id;
        this.capacity = capacity;
        this.type = type;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
