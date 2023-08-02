package com.andersenlab.dao.impl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDaoImpl implements ClientDao {

    private List<Client> clients;

    public ClientDaoImpl() {
        this.clients = new ArrayList<>();
    }

    @Override
    public Client getById(long id) {
        for (Client client : clients) {
            if (client.getId() == id)
                return client;
        }
        return null;
    }

    @Override
    public List<Client> getAll() {
        return clients;
    }

    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public void update(Client client) {
        Client existingClient = getById(client.getId());
        existingClient.setName(client.getName());
        existingClient.setCheckOutDate(client.getCheckOutDate());
        existingClient.setStatus(client.getStatus());
        existingClient.setApartment(client.getApartment());
        existingClient.setPerks(client.getPerks());
    }

    @Override
    public void remove(long id) {
        clients.removeIf(client -> client.getId() == id);
    }
}
