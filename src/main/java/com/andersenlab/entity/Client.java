package com.andersenlab.entity;


import java.time.LocalDateTime;

public class Client {
    private final long id;
    private String name;

    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
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

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
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
}
