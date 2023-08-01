package com.andersenlab.dao;

public class DataSource {

    private final ClientDao clientDao;
    private final ApartmentDao apartmentDao;
    private final PerkDao perkDao;


    public DataSource(ClientDao clientDao, ApartmentDao apartmentDao, PerkDao perkDao) {
        this.clientDao = clientDao;
        this.apartmentDao = apartmentDao;
        this.perkDao = perkDao;
    }

    public ClientDao getClientDao() {
        return clientDao;
    }

    public ApartmentDao getApartmentDao() {
        return apartmentDao;
    }

    public PerkDao getPerkDao() {
        return perkDao;
    }
}
