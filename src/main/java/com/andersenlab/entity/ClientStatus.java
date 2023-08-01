package com.andersenlab.entity;

public enum ClientStatus {
    CHECKED_IN("Client is checked in"),
    CHECKED_OUT("Client is checked out");

    private final String statusName;

    ClientStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
