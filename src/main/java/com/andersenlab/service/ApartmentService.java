package com.andersenlab.service;

import com.andersenlab.entity.Apartment;

import java.util.List;

public interface ApartmentService {

    Apartment getById(long id);

    List<Apartment> getAll();

    Apartment save(/*int apartmentNumber, */int capacity, double price);

    Apartment changePrice(long id, double price);

    Apartment changeStatus(long id);

    List<Apartment> getSorted(ApartmentSortType type);

    enum ApartmentSortType {
        ID, PRICE, CAPACITY, STATUS
    }
}
