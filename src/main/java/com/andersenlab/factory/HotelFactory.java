package com.andersenlab.factory;

import com.andersenlab.dao.*;
import com.andersenlab.dao.impl.*;
import com.andersenlab.service.*;
import com.andersenlab.service.impl.*;

public class HotelFactory {

    private final ApartmentDao apartmentDao = new ApartmentDaoImpl();
    private final ApartmentService apartmentService = new ApartmentServiceImpl(apartmentDao);
    private final PerkDao perkDao = new PerkDaoImpl();
    private final PerkService perkService = new PerkServiceImpl(perkDao);
    private final ClientDao clientDao = new ClientDaoImpl();
    private final ClientService clientService = new ClientServiceImpl(clientDao, perkDao, apartmentDao);

    private static HotelFactory instance;

    public static HotelFactory getInstance(){
        if (instance == null) {
            instance = new HotelFactory();
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
