package com.andersenlab.service;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;
import com.andersenlab.util.IdGenerator;

public class ClientService {
    private ClientDao clientDao = ClientDao.getInstance();

    public long createAndAddNewClient(String name) {
        return clientDao.addClient(new Client(name, IdGenerator.generateClientId()));
    }

    public Client getClient(long id){
        return clientDao.getClientById(id);
    }



}
