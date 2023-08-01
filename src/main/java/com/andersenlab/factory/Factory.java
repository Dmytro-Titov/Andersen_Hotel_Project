package com.andersenlab.factory;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.DataSource;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.service.ApartmentService;

import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.andersenlab.service.impl.ApartmentServiceImpl;

import com.andersenlab.service.impl.ClientServiceImpl;
import com.andersenlab.service.impl.PerkServiceImpl;


public class Factory {

    private final ApartmentDao apartmentDao = new ApartmentDao();
    private final ClientDao clientDao = new ClientDao();
    private final PerkDao perkDao = new PerkDao();
    private final DataSource dataSource = new DataSource(clientDao, apartmentDao, perkDao);
    private final ApartmentService apartmentService = new ApartmentServiceImpl(dataSource);
    private final ClientService clientService = new ClientServiceImpl(dataSource);
    private final PerkService perkService = new PerkServiceImpl(dataSource);
    private static Factory instance;

    private Factory(){}


    public static Factory getInstance() {
        if (instance == null){
            instance = new Factory();
        }
        return instance;
    }

    public ApartmentService getApartmentService() {
        return apartmentService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public PerkService getPerkService() {
        return perkService;
    }
}
