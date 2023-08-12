package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ClientDaoOnDiskImpl implements ClientDao {
    private HotelFactory hotelFactory;
    private JsonHandler jsonHandler;

    public ClientDaoOnDiskImpl(HotelFactory hotelFactory) {
        this.hotelFactory = hotelFactory;
        this.jsonHandler = new JsonHandlerImp(hotelFactory);
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
        var clients = jsonHandler.load().clientsList();
        clients.add(client);

        jsonHandler.save();
        return client;
    }

    @Override
    public Optional<Client> update(Client client) {
        var existingClient = jsonHandler.load().clientsList()
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

        jsonHandler.save();
        return existingClient;
    }

    @Override
    public boolean remove(long id) {
        var answer = jsonHandler.load().clientsList()
                .removeIf(client -> client.getId() == id);

        jsonHandler.save();
        return answer;
    }
}
