package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;

import java.util.ArrayList;
import java.util.List;

public class ApartmentDao {

    List<Apartment> apartmentList = new ArrayList<>();

    public long save(Apartment apartment){
        apartment.setId(1); //your id generator
        apartmentList.add(apartment);
        return apartment.getId();
    }

    public Apartment getApartment(long id) {

        for (Apartment apartment : apartmentList) {
            if (apartment.getId() == id) {
                return apartment;
            }
        }

        return null;
    }

    public List<Apartment> getAllApartments() {
        return apartmentList;
    }
}
