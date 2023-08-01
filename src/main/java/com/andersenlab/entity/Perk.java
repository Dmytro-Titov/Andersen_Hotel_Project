package com.andersenlab.entity;


import java.util.Objects;

public class Perk {
    private final Long id;
    private final String name;
    private double price;

    public Perk(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perk perk)) return false;
        return Objects.equals(id, perk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
