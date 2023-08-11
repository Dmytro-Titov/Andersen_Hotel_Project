package com.andersenlab.factory;

import com.andersenlab.config.Config;
import com.andersenlab.dao.impl.*;
import com.andersenlab.service.*;
import com.andersenlab.service.impl.*;

public class HotelFactory {

    private final ClientService clientService;
    private final ApartmentService apartmentService;
    private final PerkService perkService;
    private final Config config;

    public HotelFactory() {
        apartmentService = new ApartmentServiceImpl(new ApartmentDaoImpl(), this);
        perkService = new PerkServiceImpl(new PerkDaoImpl(), this);
        clientService = new ClientServiceImpl(new ClientDaoImpl(), this);
        config = new Config();
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

    public Config getConfig() {
        return config;
    }
}
