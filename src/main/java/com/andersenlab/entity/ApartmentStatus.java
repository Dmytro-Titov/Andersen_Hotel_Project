package com.andersenlab.entity;

public class ApartmentStatus {
    private int id;
    private String status;

    public ApartmentStatus() {
    }

    public ApartmentStatus(int id, String status) {
        this.id = id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ApartmentStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}

