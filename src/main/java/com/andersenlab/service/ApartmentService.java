package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApartmentService {

    public ApartmentDao apartmentDao;

    public void addApartment(int id, int apartmentNumber, int apartmentCapacity) {
        Apartment apartment = new Apartment();
        apartment.setId(id);
        apartment.setApartmentNumber(apartmentNumber);
        apartment.setApartmentCapacity(apartmentCapacity);
        apartment.setApartmentStatus(ApartmentStatus.AVAILABLE);
        apartmentDao.save(apartment);
    }

    public void changeApartmentPrice(int id, long newPrice) {
        Apartment apartment = apartmentDao.get(id);
        apartment.setApartmentPrice(newPrice);
        apartmentDao.update(id,apartment);
    }

    public void changeApartmentStatus(int id, ApartmentStatus apartmentStatus) {
        Apartment apartment = apartmentDao.get(id);
        apartment.setApartmentStatus(apartmentStatus);
        apartmentDao.update(id,apartment);
    }

    public List<Apartment> sortedByPrice() {
        List<Apartment> apartments = new ArrayList<>(apartmentDao.getApartments());
        apartments.sort(Comparator.comparing(Apartment::getApartmentPrice));
        return apartments;
    }

    public List<Apartment> sortedByCapacity() {
        List<Apartment> apartments = new ArrayList<>(apartmentDao.getApartments());
        apartments.sort(Comparator.comparing(Apartment::getApartmentCapacity));
        return apartments;
    }

    public List<Apartment> sortedByStatus() {
        List<Apartment> apartments = new ArrayList<>(apartmentDao.getApartments());
        apartments.sort(Comparator.comparing(Apartment::getApartmentStatus));
        return apartments;
    }

}
