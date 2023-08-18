package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;

import java.util.List;
import java.util.Optional;

public class HibernateClientDaoImpl implements ClientDao {
    @Override
    public Optional<Client> getById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Client save(Client client) {
        return null;
    }

    @Override
    public Optional<Client> update(Client client) {
        return Optional.empty();
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public List<Client> getSortedBy(ClientSortType type) {
        return null;
    }
}
