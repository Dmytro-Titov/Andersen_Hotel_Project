package com.andersenlab.service.impl;

import com.andersenlab.dao.DataSource;
import com.andersenlab.service.ClientService;

public class ClientServiceImpl implements ClientService {

    private final DataSource dataSource;

    public ClientServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void addClient(String name) {
        dataSource.getClientDao().addClient(name);
    }

    @Override
    public void getClients() {
        dataSource.getClientDao().getClients().forEach(client -> System.out.println(client.getName() + " - " + client.getId()));
    }
}
