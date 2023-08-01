package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.util.IdGenerator;

public class ApartmentService {

    private final ClientDao clientDao = ClientDao.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    public Long createAndAddNewApartment(double price, int capacity ) {
        return apartmentDao.addApartment(new Apartment(IdGenerator.generateApartmentId(), price, capacity));
    }

    public Apartment getApartment(long id){
        return apartmentDao.getApartmentById(id);
    }



}
