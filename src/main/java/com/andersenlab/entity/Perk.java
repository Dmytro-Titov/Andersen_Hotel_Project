package com.andersenlab.entity;

import java.util.Objects;

public class Perk {
    private final int id;
    private String name;
    private double price;

    public Perk(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Perk{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perk perk = (Perk) o;
        return id == perk.id && Double.compare(perk.price, price) == 0 && Objects.equals(name, perk.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }
}
