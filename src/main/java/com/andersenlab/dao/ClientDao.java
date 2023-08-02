package com.andersenlab.dao;

import com.andersenlab.entity.Client;
import java.util.*;
import java.util.stream.Collectors;

public class ClientDao {
    private static ClientDao instance;
    private Map<Long, Client> clients = new HashMap<>();

    private ClientDao() {
    }

    public static ClientDao getInstance() {
        if (instance == null) {
            instance = new ClientDao();
        }
        return instance;
    }

    public Long addClient(Client newClient) {
        clients.put(newClient.getId(), newClient);
        return newClient.getId();
    }

    public Client getClientById(Long id) {
        return clients.get(id);
    }

    public Collection<Client> getAllClients() {
        return clients.values();
    }

    public void removeClient(Client client) {
        clients.remove(client.getId());
    }

    public boolean isClientExist(Long id) {
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
