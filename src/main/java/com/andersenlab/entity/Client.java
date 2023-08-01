package com.andersenlab.entity;


import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Client {
    private final int id;
    private String name;
    private LocalDate checkOutDay;
    private boolean status;
    private Apartment apartment;
    private List<Perk> perks;

    public Client(int id, String name, LocalDate checkOutDay, List<Perk> perks) {
        this.id = id;
        this.name = name;
        this.checkOutDay = checkOutDay;
        this.perks = perks;
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

    public LocalDate getCheckOutDay() {
        return checkOutDay;
    }

    public void setCheckOutDay(LocalDate checkOutDay) {
        this.checkOutDay = checkOutDay;
    }

    public boolean hasStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public List<Perk> getPerks() {
        return perks;
    }

    public void setPerks(List<Perk> perks) {
        this.perks = perks;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkOutDay=" + checkOutDay +
                ", status=" + status +
                ", apartment=" + apartment +
                ", perks=" + perks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && status == client.status && Objects.equals(name, client.name) && Objects.equals(checkOutDay, client.checkOutDay) && Objects.equals(apartment, client.apartment) && Objects.equals(perks, client.perks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, checkOutDay, status, apartment, perks);
    }
}
