package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.util.IdGenerator;

import java.util.List;

public class ApartmentService {

    private final ClientDao clientDao = ClientDao.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    private final PerkDao perkDao = PerkDao.getInstance();

    public Long createAndAddNewApartment(double price, int capacity) {
        return apartmentDao.addApartment(new Apartment(IdGenerator.generateApartmentId(), price, capacity));
    }

    public Apartment getApartment(Long id) {
        return apartmentDao.getApartmentById(id);
    }

    public List<Apartment> showAllApartments(int order) {
        switch (order) {
            case 2:
                return apartmentDao.sortByCapacity();
            case 3:
                return apartmentDao.sortByAvailability();
            default:
                return apartmentDao.sortByPrice();
        }
    }

    public void changePriceOfApartment(Long id, double newPrice) {
        Apartment apartment = getApartment(id);
        if(apartment != null) {
            apartment.setPrice(newPrice);
            System.out.println("The price has been successfully changed!");
        }
    }

    public void reverseStatus(Long id) {
        Apartment apartment = getApartment(id);
        if(apartment != null) {
            apartment.setAvailability(!apartment.isAvailable());
            System.out.println("Status was successfully changed!");
        }
    }



}
