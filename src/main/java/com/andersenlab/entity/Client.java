package com.andersenlab.entity;


import java.time.Instant;

public class Client {
    private final long id;
    private String name;

    private Instant checkInDate;
    private Instant checkOutDate;
    private ClientStatus status;

    public Client(String name, long id) {
        this.id = id;
        this.name = name;
        status = ClientStatus.CHECKED_OUT;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Instant checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Instant getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Instant checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
    }
}
