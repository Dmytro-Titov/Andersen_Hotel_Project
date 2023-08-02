package com.andersenlab.dao;

import com.andersenlab.entity.Client;

import java.util.*;
import java.util.stream.Collectors;

public class ClientDao {
    private Map<Long, Client> clients = new HashMap<>();

    public long addClient(Client newClient) {
        clients.put(newClient.getId(), newClient);
        return newClient.getId();
    }

    public Client getClientById(long id) {
        return clients.get(id);
    }

    public List<Client> getAllClients() {
        return clients.values().stream().toList();
    }

    public void removeClient(Client client) {
        clients.remove(client.getId());
    }

    public boolean isClientExist(long id) {
        return clients.containsKey(id);
    }

    public List<Client> sortByName() {
        return clients.values()
                .stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());
    }

    public List<Client> sortByCheckOutDate() {
        return clients.values()
                .stream()
                .sorted(Comparator.comparing(Client::getCheckOutDate))
                .collect(Collectors.toList());
    }

    public List<Client> sortByStatus() {
        return clients.values()
                .stream()
                .sorted(Comparator.comparing(Client::isLives))
                .collect(Collectors.toList());
    }

}
