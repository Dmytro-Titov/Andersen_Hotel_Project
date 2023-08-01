package com.andersenlab.entity;

public abstract class BasicPerk {
    protected String name;
    protected int id;
    protected double price;

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public abstract void apply();
}
