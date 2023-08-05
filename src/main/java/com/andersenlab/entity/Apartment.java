package com.andersenlab.entity;

import java.util.Objects;

public class Apartment {
    private long id;
    private int capacity;
    private double price;
    private ApartmentStatus status;

    public Apartment() {
    }

    public Apartment(long id, double price) {
        this.id = id;
        this.price = price;
    }

    public Apartment(long id, ApartmentStatus status) {
        this.id = id;
        this.status = status;
    }

    public Apartment(long id, int capacity, double price, ApartmentStatus status) {
        this.id = id;
        this.capacity = capacity;
        this.price = price;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && capacity == apartment.capacity
                && Double.compare(apartment.price, price) == 0
                && status == apartment.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, capacity, price, status);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", capacity=" + capacity +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
