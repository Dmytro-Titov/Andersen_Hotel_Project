package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Client;
import com.andersenlab.util.IdGenerator;

public class ClientService {
    private final ClientDao clientDao = ClientDao.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    private final PerkDao perkDao = PerkDao.getInstance();

    public Long createAndAddNewClient(String name) {
        return clientDao.addClient(new Client(IdGenerator.generateClientId(), name));
    }

    public Client getClient(Long id){
        return clientDao.getClientById(id);
    }



}
