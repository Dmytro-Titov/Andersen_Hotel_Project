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
        //Some method add client to collection
    }
}
