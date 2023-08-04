package com.andersenlab.service;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;

import java.util.List;

public interface ApartmentService {

    Apartment getById(long id);

    Apartment save(int apartmentNumber, int capacity, double price);

    Apartment changePrice(long id, double price);

    Apartment changeStatus(long id, ApartmentStatus status);

    List<Apartment> sortByPrice();

    List<Apartment> sortByCapacity();

    List<Apartment> sortByStatus();
}
