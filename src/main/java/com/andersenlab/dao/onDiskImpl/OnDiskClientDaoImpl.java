package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OnDiskClientDaoImpl implements ClientDao {
    private final OnDiskJsonHandler onDiskJsonHandler;
    public OnDiskClientDaoImpl(HotelFactory hotelFactory) {
        this.onDiskJsonHandler = new OnDiskJsonHandler(hotelFactory);
    }

    @Override
    public Optional<Client> getById(long id) {
        return onDiskJsonHandler.load().getClientsList()
                .stream()
                .filter(client -> client.getId() == id)
                .findFirst();
    }

    @Override
    public List<Client> getAll() {
        return onDiskJsonHandler.load().getClientsList();
    }

    @Override
    public Client save(Client client) {
        var stateEntity = onDiskJsonHandler.load();
        var clients = stateEntity.getClientsList();
        var copy = new ArrayList<>(clients);
        copy.add(client);

        onDiskJsonHandler.save(stateEntity.addClientList(copy));
        return client;
    }

    @Override
    public Optional<Client> update(Client client) {
        var stateEntity = onDiskJsonHandler.load();
        var existingClient = stateEntity.getClientsList()
                .stream()
                .filter(client1 -> Objects.equals(client1.getId(), client.getId()))
                .findFirst();

        existingClient.ifPresent(updClient -> {
            existingClient.get().setName(client.getName());
            existingClient.get().setCheckOutDate(client.getCheckOutDate());
            existingClient.get().setCheckInDate(client.getCheckInDate());
            existingClient.get().setStatus(client.getStatus());
            existingClient.get().setApartment(client.getApartment());
            existingClient.get().setStayCost(client.getStayCost());
            existingClient.get().setPerks(client.getPerks());
        });

        onDiskJsonHandler.save(stateEntity);
        return existingClient;
    }

    @Override
    public boolean remove(long id) {
        var stateEntity = onDiskJsonHandler.load();
        var answer = stateEntity.getClientsList()
                .removeIf(client -> client.getId() == id);

        onDiskJsonHandler.save(stateEntity);
        return answer;
    }
}
