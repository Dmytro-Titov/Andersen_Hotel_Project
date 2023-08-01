package com.andersenlab.entity;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client {
    private final Long id;
    private String name;

    private LocalDateTime checkOutDate;
    private boolean isLives;

    private Apartment apartment;
    private Set<Perk> perks;

    private int currentPriceToPay;

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
        isLives = false;
        perks = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isLives() {
        return isLives;
    }

    public void setLives(boolean lives) {
        isLives = lives;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Set<Perk> getPerks() {
        return perks;
    }

    public void addPerk(Perk perk) {
        perks.add(perk);
    }

    public int getCurrentPriceToPay() {
        return currentPriceToPay;
    }

    public void setCurrentPriceToPay(int currentPriceToPay) {
        this.currentPriceToPay = currentPriceToPay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkOutDate=" + checkOutDate +
                ", isLives=" + isLives +
                ", apartment=" + apartment +
                ", perks=" + perks +
                ", currentPriceToPay=" + currentPriceToPay +
                '}';
    }
}
