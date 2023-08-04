package com.andersenlab.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private long id;
    private String name;
    private LocalDateTime checkOutDate;
    private LocalDateTime checkInDate;
    private ClientStatus status;
    private Apartment apartment;
    private List<Perk> perks;
    private double stayCost;
    private int quantityOfPeople;

    public Client(long id, String name, int quantityOfPeople) {
        this.id = id;
        this.name = name;
        this.quantityOfPeople = quantityOfPeople;
        status = ClientStatus.NEW;
        perks = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
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

    public double getStayCost() {
        return stayCost;
    }

    public void setStayCost(double stayCost) {
        this.stayCost = stayCost;
    }

    public int getQuantityOfPeople() {
        return quantityOfPeople;
    }

    public void setQuantityOfPeople(int quantityOfPeople) {
        this.quantityOfPeople = quantityOfPeople;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return id == client.id && Objects.equals(name, client.name) && Objects.equals(checkOutDate, client.checkOutDate) && status == client.status && Objects.equals(apartment, client.apartment) && Objects.equals(perks, client.perks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, checkOutDate, status, apartment, perks);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checkOutDate=" + checkOutDate +
                ", status=" + status +
                ", apartment=" + apartment +
                ", perks=" + perks +
                '}';
    }
}
