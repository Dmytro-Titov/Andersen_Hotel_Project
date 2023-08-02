package com.andersenlab.factory;

import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;

public class HotelFactory {

    private ApartmentService apartmentService = new ApartmentService();
    private ClientService clientService = new ClientService();
    private PerkService perkService = new PerkService();
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
