package com.andersenlab.dao.impl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApartmentDaoImpl implements ApartmentDao {

    private final List<Apartment> apartments;

    public ApartmentDaoImpl() {
        this.apartments = new ArrayList<>();
    }

    @Override
    public Optional<Apartment> getById(long id) {
        return apartments.stream()
                .filter(apartment -> apartment.getId() == id)
                .findFirst();
    }

    @Override
    public List<Apartment> getAll() {
        return apartments;
    }

    @Override
    public Apartment save(Apartment apartment) {
        apartments.add(apartment);
        return apartment;
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        Optional<Apartment> existingApartment = getById(apartment.getId());
        if (existingApartment.isPresent()) {
            if (apartment.getPrice() != 0.0) {
                existingApartment.get().setPrice(apartment.getPrice());
            }
            if (apartment.getCapacity() != 0) {
                existingApartment.get().setCapacity(apartment.getCapacity());
            }
            if (apartment.getStatus() != null) {
                existingApartment.get().setStatus(apartment.getStatus());
            }
            return existingApartment;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean remove(long id) {
        return apartments.removeIf(apartment -> apartment.getId() == id);
    }
}
