package com.andersenlab.entity;

import java.util.List;

public class Apartment {
    private int id;
    private int apartmentNumber;
    private ApartmentType apartmentType;
    private ApartmentAvailability apartmentAvailability;

    public Apartment(int id, int apartmentNumber, ApartmentType apartmentType) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.apartmentType = apartmentType;
        apartmentAvailability = ApartmentAvailability.AVAILABLE;
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

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public ApartmentAvailability getApartmentAvailability() {
        return apartmentAvailability;
    }

    public void setApartmentAvailability(ApartmentAvailability apartmentAvailability) {
        this.apartmentAvailability = apartmentAvailability;
    }
}
