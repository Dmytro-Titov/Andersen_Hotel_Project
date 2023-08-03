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
        return apartmentDao.getById(id);
    }

    @Override
    public void save(int apartmentNumber, int capacity, double price) {
        Apartment apartment = new Apartment(IdGenerator.generateApartmentId(),
                apartmentNumber, capacity, price, ApartmentStatus.AVAILABLE);
        apartmentDao.save(apartment);
    }

    @Override
    public void changePrice(long id, double price) {
        apartmentDao.update(new Apartment(id, price));
    }

    @Override
    public void changeStatus(long id, ApartmentStatus status) {
        apartmentDao.update(new Apartment(id, status));
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
