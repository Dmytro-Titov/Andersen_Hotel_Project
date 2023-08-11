package com.andersenlab.service.impl;

import com.andersenlab.config.Config;
import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.util.EntityValidityCheck;
import com.andersenlab.util.IdGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class ApartmentServiceImpl implements ApartmentService {

    private final ApartmentDao apartmentDao;

    public ApartmentServiceImpl(ApartmentDao apartmentDao, HotelFactory hotelFactory) {
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
    public Apartment save(int capacity, double price) {
        EntityValidityCheck.apartmentCapacityCheck(capacity);
        EntityValidityCheck.apartmentPriceCheck(price);
        Apartment apartment = new Apartment(IdGenerator.generateApartmentId(),
                capacity, price, ApartmentStatus.AVAILABLE);
        return apartmentDao.save(apartment);
    }

    @Override
    public void save(List<Apartment> apartments) {
        apartments.forEach(apartment -> {
            apartmentDao.save(apartment);
            IdGenerator.generateApartmentId();
        });
    }

    @Override
    public Apartment update(Apartment apartment) {
        return apartmentDao.update(apartment)
                .orElseThrow(() -> new RuntimeException("Apartment with this id doesn't exist. Id: " + apartment.getId()));
    }

    @Override
    public Apartment changePrice(long id, double price) {
        EntityValidityCheck.apartmentPriceCheck(price);
        return update(new Apartment(id, price));
    }

    @Override
    public Apartment changeStatus(long id) {
        boolean allowStatusChange = Config.INSTANCE.getConfigData().getApartment().isAllowApartmentStatusChange();
        if (!allowStatusChange) {
            throw new IllegalArgumentException("Configuration does not allow change of status");
        }
        ApartmentStatus newStatus = getById(id).getStatus() == ApartmentStatus.AVAILABLE ?
                ApartmentStatus.UNAVAILABLE : ApartmentStatus.AVAILABLE;
        return update(new Apartment(id, newStatus));
    }

    @Override
    public List<Apartment> getSorted(ApartmentSortType type) {
        return switch (type) {
            case ID -> getAll();
            case PRICE -> sortBy(Apartment::getPrice);
            case CAPACITY -> sortBy(Apartment::getCapacity);
            case STATUS -> sortBy(Apartment::getStatus);
        };
    }
    private List<Apartment> sortBy(Function<Apartment, Comparable> extractor) {
        return getAll().stream()
                .sorted(Comparator.comparing(extractor))
                .toList();
    }
}
