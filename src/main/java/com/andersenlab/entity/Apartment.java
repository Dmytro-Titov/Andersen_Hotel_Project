package com.andersenlab.entity;

import java.util.List;

public class Apartment {
    private int id;
    private int apartmentNumber;
    private ApartmentType apartmentType;
    private ApartmentStatus apartmentStatus;
    private List<Perk> perkList;

    public Apartment() {
    }

    public Apartment(int id, int apartmentNumber, ApartmentType apartmentType, ApartmentStatus apartmentStatus, List<Perk> perkList) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.apartmentType = apartmentType;
        this.apartmentStatus = apartmentStatus;
        this.perkList = perkList;
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

    public List<Perk> getPerkList() {
        return perkList;
    }

    public void setPerkList(List<Perk> perkList) {
        this.perkList = perkList;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", apartmentNumber=" + apartmentNumber +
                ", apartmentType=" + apartmentType +
                ", apartmentStatus=" + apartmentStatus +
                ", perkList=" + perkList +
                '}';
    }
}
