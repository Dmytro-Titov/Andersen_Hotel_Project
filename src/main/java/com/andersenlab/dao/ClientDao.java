package com.andersenlab.dao;

import com.andersenlab.entity.Client;

import java.util.*;

public interface ClientDao {
    Client getById(long id);

    List<Client> getAll();

    void save(Client client);

    void update(Client client);

    void remove(long id);
}
