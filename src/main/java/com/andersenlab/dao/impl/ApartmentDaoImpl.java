package com.andersenlab.dao.impl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;

import java.util.ArrayList;
import java.util.List;

public class ApartmentDaoImpl implements ApartmentDao {

    private List<Apartment> apartments;

    public ApartmentDaoImpl() {
        this.apartments = new ArrayList<>();
    }

    @Override
    public Apartment getById(long id) {
        for (Apartment apartment : apartments) {
            if (apartment.getId() == id)
                return apartment;
        }
        return null;
    }

    @Override
    public List<Apartment> getAll() {
        return apartments;
    }

    @Override
    public void save(Apartment apartment) {
        apartments.add(apartment);
    }

    @Override
    public void update(Apartment apartment) {
        Apartment existingApartment = getById(apartment.getId());
        if (apartment.getPrice() != 0.0) {
            existingApartment.setPrice(apartment.getPrice());
        }
        if (apartment.getApartmentNumber() != 0) {
            existingApartment.setApartmentNumber(apartment.getApartmentNumber());
        }
        if (apartment.getCapacity() != 0) {
            existingApartment.setCapacity(apartment.getCapacity());
        }
        if (apartment.getStatus() != null) {
            existingApartment.setStatus(apartment.getStatus());
        }
    }

    @Override
    public void remove(long id) {
        apartments.removeIf(apartment -> apartment.getId() == id);
    }
}
