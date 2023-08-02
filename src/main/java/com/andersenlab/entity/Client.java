package com.andersenlab.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Client {
    private long id;
    private String name;
    private LocalDateTime checkOutDate;
    private ClientStatus status;
    private Apartment apartment;
    private List<Perk> perks;

    public Client(long id, String name) {
        this.id = id;
        this.name = name;
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
                ", status=" + status +
                ", apartment=" + apartment +
                ", perks=" + perks +
                '}';
    }
}
