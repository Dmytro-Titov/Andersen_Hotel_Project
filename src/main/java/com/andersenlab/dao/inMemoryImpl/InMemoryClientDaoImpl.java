package com.andersenlab.dao.inMemoryImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryClientDaoImpl implements ClientDao {

    private final List<Client> clients;

    public InMemoryClientDaoImpl() {
        this.clients = new ArrayList<>();
    }

    @Override
    public Optional<Client> getById(long id) {
        return clients.stream()
                .filter(client -> client.getId() == id)
                .findFirst();
    }

    @Override
    public List<Client> getAll() {
        return new ArrayList<>(clients);
    }

    @Override
    public Client save(Client client) {
        clients.add(client);
        return client;
    }

    @Override
    public Optional<Client> update(Client client) {
        Optional<Client> existingClient = getById(client.getId());
        existingClient.ifPresent(updClient -> {
            existingClient.get().setName(client.getName());
            existingClient.get().setCheckOutDate(client.getCheckOutDate());
            existingClient.get().setStatus(client.getStatus());
            existingClient.get().setApartment(client.getApartment());
            existingClient.get().setPerks(client.getPerks());
        });
        return existingClient;
    }

    @Override
    public boolean remove(long id) {
        return clients.removeIf(client -> client.getId() == id);
    }
}
