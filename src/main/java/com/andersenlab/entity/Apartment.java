package com.andersenlab.entity;

public class Apartment {
    private int id;
    private int apartmentNumber;
    private ApartmentType apartmentType;
    private ApartmentStatus apartmentStatus;

    public Apartment(int id, int apartmentNumber, ApartmentType apartmentType) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.apartmentType = apartmentType;
        apartmentStatus = ApartmentStatus.AVAILABLE;
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

    public ApartmentStatus getApartmentStatus() {
        return apartmentStatus;
    }

    public void setApartmentStatus(ApartmentStatus apartmentStatus) {
        this.apartmentStatus = apartmentStatus;
    }
}
