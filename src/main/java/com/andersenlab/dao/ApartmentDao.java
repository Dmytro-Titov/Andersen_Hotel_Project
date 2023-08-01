package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ApartmentDao {
    private List<Apartment> apartments;

    public ApartmentDao() {
        this.apartments = new ArrayList<>();
        this.apartments.add(new Apartment(1, 1000.0, 2, true));
        this.apartments.add(new Apartment(2, 1500.0, 2, true));
        this.apartments.add(new Apartment(3, 2000.0, 4, true));
        this.apartments.add(new Apartment(4, 3000.0, 4, true));
        this.apartments.add(new Apartment(5, 3500.0, 6, true));
        this.apartments.add(new Apartment(6, 1000.0, 6, true));
    }

    public boolean bookApartment(Apartment apartment) {
        boolean successfulStatusChange = false;
        for (Apartment room : apartments) {
            if (room.equals(apartment)) {
                room.setAvailability(false);
                successfulStatusChange = true;
            }
        } return successfulStatusChange;
    }
    public boolean unBookApartment(Apartment apartment) {
        boolean successfulStatusChange = false;
        for (Apartment room : apartments) {
            if (room.equals(apartment)) {
                room.setAvailability(true);
                successfulStatusChange = true;
            }
        } return successfulStatusChange;
    }
    public Apartment changeApartmentPrice(Apartment apartment, double newPrice) {
        Apartment result = null;
        for (Apartment room : apartments) {
            if (room.equals(apartment)) {
                room.setPrice(newPrice);
                result = room;
            }
        } return result;
    }
    public Apartment add(Apartment apartment) {
        apartments.add(apartment);
        return apartments.get(apartments.size() - 1);
    }
    public List<Apartment> listByPrice() {
        List<Apartment> sortedByPrice = new ArrayList<>(apartments);
        sortedByPrice.sort(Comparator.comparing(Apartment::getPrice));
        return sortedByPrice;
    }
    public List<Apartment> listByCapacity() {
        List<Apartment> sortedByCapacity = new ArrayList<>(apartments);
        sortedByCapacity.sort(Comparator.comparing(Apartment::getCapacity));
        return sortedByCapacity;
    }
    public List<Apartment> listByAvailability() {
        List<Apartment> sortedByAvailability = new ArrayList<>(apartments);
        sortedByAvailability.sort(Comparator.comparing(Apartment::isAvailable));
        return sortedByAvailability;
    }
}
