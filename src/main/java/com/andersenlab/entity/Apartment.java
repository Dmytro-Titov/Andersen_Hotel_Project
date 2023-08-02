package com.andersenlab.entity;

import java.util.Objects;

public class Apartment {
    private int id;
    private int apartmentNumber;
    private int apartmentCapacity;
    private long apartmentPrice;
    private ApartmentStatus apartmentStatus;

    public Apartment() {
    }

    public Apartment(int id, int apartmentNumber, int apartmentCapacity, long apartmentPrice, ApartmentStatus apartmentStatus) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.apartmentCapacity = apartmentCapacity;
        this.apartmentPrice = apartmentPrice;
        this.apartmentStatus = apartmentStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getApartmentCapacity() {
        return apartmentCapacity;
    }

    public void setApartmentCapacity(int apartmentCapacity) {
        this.apartmentCapacity = apartmentCapacity;
    }

    public long getApartmentPrice() {
        return apartmentPrice;
    }

    public void setApartmentPrice(long apartmentPrice) {
        this.apartmentPrice = apartmentPrice;
    }

    public ApartmentStatus getApartmentStatus() {
        return apartmentStatus;
    }

    public void setApartmentStatus(ApartmentStatus apartmentStatus) {
        this.apartmentStatus = apartmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apartment apartment = (Apartment) o;
        return id == apartment.id && apartmentNumber == apartment.apartmentNumber && apartmentCapacity == apartment.apartmentCapacity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apartmentNumber, apartmentCapacity);
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentCapacity=" + apartmentCapacity +
                ", apartmentPrice=" + apartmentPrice +
                ", apartmentStatus=" + apartmentStatus +
                '}';
    }
}
