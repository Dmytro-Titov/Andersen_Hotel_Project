package com.andersenlab.service.impl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.util.IdGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentDao apartmentDao;

    public ApartmentServiceImpl(ApartmentDao apartmentDao) {
        this.apartmentDao = apartmentDao;
    }

    @Override
    public Apartment getById(long id) {
        return apartmentDao.getById(id)
                .orElseThrow(() -> new RuntimeException("Apartment with this id doesn't exist. Id: " + id));
    }

    @Override
    public List<Apartment> getAll() {
        return apartmentDao.getAll();
    }

    @Override
    public Apartment save(/*int apartmentNumber,*/ int capacity, double price) {
        Apartment apartment = new Apartment(IdGenerator.generateApartmentId(),
                /*apartmentNumber,*/ capacity, price, ApartmentStatus.AVAILABLE);
        return apartmentDao.save(apartment);
    }

    @Override
    public Apartment changePrice(long id, double price) {
        return apartmentDao.update(new Apartment(id, price))
                .orElseThrow(() -> new RuntimeException("Apartment with this id doesn't exist. Id: " + id));
    }

    @Override
    public Apartment changeStatus(long id) {
        Apartment apartment = getById(id);
        ApartmentStatus newStatus = apartment.getStatus() == ApartmentStatus.AVAILABLE ?
                ApartmentStatus.UNAVAILABLE : ApartmentStatus.AVAILABLE;
        return apartmentDao.update(new Apartment(id, newStatus))
                .orElseThrow(() -> new RuntimeException("Apartment with this id doesn't exist. Id: " + id));
    }

    @Override
    public List<Apartment> getSorted(ApartmentSortType type) {
        return switch (type) {
            case ID -> getAll();
            case PRICE -> sortByPrice();
            case CAPACITY -> sortByCapacity();
            case STATUS -> sortByStatus();
        };
    }

    @Override
    public List<Apartment> sortByPrice() {
        List<Apartment> sortedByPrice = new ArrayList<>(apartmentDao.getAll());
        sortedByPrice.sort(Comparator.comparing(Apartment::getPrice));
        return sortedByPrice;
    }

    @Override
    public List<Apartment> sortByCapacity() {
        List<Apartment> sortedByCapacity = new ArrayList<>(apartmentDao.getAll());
        sortedByCapacity.sort(Comparator.comparing(Apartment::getCapacity));
        return sortedByCapacity;
    }

    @Override
    public List<Apartment> sortByStatus() {
        List<Apartment> sortedByStatus = new ArrayList<>(apartmentDao.getAll());
        sortedByStatus.sort(Comparator.comparing(Apartment::getStatus));
        return sortedByStatus;
    }
}
