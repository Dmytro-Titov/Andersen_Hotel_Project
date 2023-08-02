package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;

import java.util.ArrayList;
import java.util.List;

public class ApartmentDao {
    private List<Apartment> apartments;

    public ApartmentDao () {
        this.apartments = new ArrayList<>();
    }

    public Apartment get(int id) {
        for (Apartment apartment: apartments) {
            if (apartment.getId() == id)
                return apartment;
        }
        return null;
    }

    public void add(Apartment apartment) {
        apartments.add(apartment);
    }

    public void update(int id, Apartment updatedApartment){
        Apartment apartment = get(id);
        apartment.setApartmentNumber(updatedApartment.getApartmentNumber());
        apartment.setApartmentCapacity(updatedApartment.getApartmentCapacity());
        apartment.setApartmentPrice(updatedApartment.getApartmentPrice());
        apartment.setApartmentStatus(updatedApartment.getApartmentStatus());

    }

    public List<Apartment> getApartments () {
        return apartments;
    }


}
