package com.andersenlab.dao;

import com.andersenlab.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    List<Client> clients = new ArrayList<>();
    private int i = 1;

    public void addClient(String name) {
        Client client = new Client(name, i++);
        clients.add(client);
    }

    public List<Client> getClients(){
        return clients;
    }
}
