package com.andersenlab.dao;

import com.andersenlab.entity.Client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class ClientDao {
    private static ClientDao instance;
    private List<Client> clients = new ArrayList<>();

    private ClientDao() {}

    public static ClientDao getInstance() {
        if (instance == null) {
            instance = new ClientDao();
        }
        return instance;
    }

    public long addClient(Client newClient) {
        clients.add(newClient);
        return newClient.getId();
    }

    public Client getClientById(long id) {
        return clients.stream().
                filter(client -> client.getId() == id).
                findFirst().
                orElseThrow(() -> new NoSuchElementException("Client with ID " + id + " not found"));
    }

    public List<Client> getAllClients() {
        return new ArrayList<>(clients);
    }

    public void removeClient(Client newClient) {
        clients.remove(newClient);

    }

    public List<Client> sortByName(){
        return clients.stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());
    }

    public List<Client> sortByCheckOutDate(){
        return clients.stream()
                .sorted(Comparator.comparing(Client::getCheckOutDate))
                .collect(Collectors.toList());
    }

    public List<Client> sortByStatus() {
        return clients.stream()
                .sorted(Comparator.comparing(Client::isLives))
                .collect(Collectors.toList());
    }
}
