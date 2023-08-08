package com.andersenlab.dao;

import com.andersenlab.entity.Client;

import java.util.*;

public interface ClientDao {
    Optional<Client> getById(long id);

    List<Client> getAll();

    Client save(Client client);

    Optional<Client> update(Client client);

    boolean remove(long id);
}
