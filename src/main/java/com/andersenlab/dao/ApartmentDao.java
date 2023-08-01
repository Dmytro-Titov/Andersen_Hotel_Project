package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;

import java.util.*;
import java.util.stream.Collectors;

public class ApartmentDao {

    private static ApartmentDao instance;
    private Map<Long, Apartment> apartments = new HashMap<>();

    private ApartmentDao() {
    }

    public static ApartmentDao getInstance() {
        if (instance == null) {
            instance = new ApartmentDao();
        }
        return instance;
    }

    public long addApartment(Apartment newApartment) {
        apartments.put(newApartment.getId(), newApartment);
        return newApartment.getId();
    }

    public Apartment getApartmentById(Long id) {
        return apartments.get(id);
    }

    public Collection<Apartment> getAllApartments() {
        return apartments.values();
    }

    public void removeApartment(Apartment client) {
        apartments.remove(client.getId());
    }

    public boolean isApartmentExist(Long id) {
        return apartments.containsKey(id);
    }

    public List<Apartment> sortByPrice() {
        return apartments.values()
                .stream()
                .sorted(Comparator.comparing(Apartment::getPrice))
                .collect(Collectors.toList());
    }

    public List<Apartment> sortByCapacity() {
        return apartments.values()
                .stream()
                .sorted(Comparator.comparing(Apartment::getCapacity))
                .collect(Collectors.toList());
    }

    public List<Apartment> sortByAvailability() {
        return apartments.values()
                .stream()
                .sorted(Comparator.comparing(Apartment::isAvailable))
                .collect(Collectors.toList());
    }


}
