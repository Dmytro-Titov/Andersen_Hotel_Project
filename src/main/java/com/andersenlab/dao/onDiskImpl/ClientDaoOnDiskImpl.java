package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientDaoOnDiskImpl implements ClientDao {
    private final JsonHandler jsonHandler = new JsonHandlerImp();

    public ClientDaoOnDiskImpl() {
    }

    @Override
    public Optional<Client> getById(long id) {
        return jsonHandler.load().clientsList()
                .stream()
                .filter(client -> client.getId() == id)
                .findFirst();
    }

    @Override
    public List<Client> getAll() {
        return jsonHandler.load().clientsList();
    }

    @Override
    public Client save(Client client) {
        var stateEntity = jsonHandler.load();
        var clients = stateEntity.clientsList();
        clients.add(client);

        jsonHandler.save(stateEntity);
        return client;
    }

    @Override
    public Optional<Client> update(Client client) {
        var stateEntity = jsonHandler.load();
        var existingClient = stateEntity.clientsList()
                .stream()
                .filter(client1 -> Objects.equals(client1.getId(), client.getId()))
                .findFirst();

        existingClient.ifPresent(updClient -> {
            existingClient.get().setName(client.getName());
            existingClient.get().setCheckOutDate(client.getCheckOutDate());
            existingClient.get().setStatus(client.getStatus());
            existingClient.get().setApartment(client.getApartment());
            existingClient.get().setPerks(client.getPerks());
        });

        jsonHandler.save(stateEntity);
        return existingClient;
    }

    @Override
    public boolean remove(long id) {
        var stateEntity = jsonHandler.load();
        var answer = stateEntity.clientsList()
                .removeIf(client -> client.getId() == id);

        jsonHandler.save(stateEntity);
        return answer;
    }
}
