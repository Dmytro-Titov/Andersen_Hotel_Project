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

}
