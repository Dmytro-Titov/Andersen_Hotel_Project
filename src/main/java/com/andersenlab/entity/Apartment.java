package com.andersenlab.entity;

import java.util.Objects;

public class Apartment {
    private final int id;
    private double price;
    private int capacity;
    private boolean availability;

    public Apartment(int id, double price, int capacity, boolean availability) {
        this.id = id;
        this.price = price;
        this.capacity = capacity;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", price=" + price +
                ", capacity=" + capacity +
                ", availability=" + availability +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && Double.compare(apartment.price, price) == 0 && capacity == apartment.capacity && availability == apartment.availability;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, capacity, availability);
    }
}
