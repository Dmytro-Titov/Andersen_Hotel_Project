package com.andersenlab.entity;

public class Apartment {

    long id;
    int capacity;
    boolean available = true;

    public Apartment(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void changeStatus() {
        available = !available;
    }
}
