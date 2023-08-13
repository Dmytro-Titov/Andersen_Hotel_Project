package com.andersenlab.factory;

import com.andersenlab.config.Config;
import com.andersenlab.dao.onDiskImpl.OnDiskApartmentDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskClientDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskPerkDaoImpl;
import com.andersenlab.service.*;
import com.andersenlab.service.impl.*;

public class HotelFactory {

    private final ClientService clientService;
    private final ApartmentService apartmentService;
    private final PerkService perkService;
    private final Config config;

    public HotelFactory(Config config) {
        this.config = config;
        apartmentService = new ApartmentServiceImpl(new OnDiskApartmentDaoImpl(this), this);
        perkService = new PerkServiceImpl(new OnDiskPerkDaoImpl(this), this);
        clientService = new ClientServiceImpl(new OnDiskClientDaoImpl(this), this);
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
