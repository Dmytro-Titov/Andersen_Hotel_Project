package com.andersenlab.service.impl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.*;
import com.andersenlab.service.ClientService;
import com.andersenlab.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final PerkDao perkDao;
    private final ApartmentDao apartmentDao;

    public ClientServiceImpl(ClientDao clientDao, PerkDao perkDao, ApartmentDao apartmentDao) {
        this.clientDao = clientDao;
        this.perkDao = perkDao;
        this.apartmentDao = apartmentDao;
    }

    @Override
    public Client getById(long id) {
        return clientDao.getById(id)
                .orElseThrow(() -> new RuntimeException("Client with this id doesn't exist. Id: " + id));
    }

    @Override
    public Client save(String name, int quantityOfPeople) {
        Client client = new Client(IdGenerator.generateClientId(), name, quantityOfPeople);
        return clientDao.save(client);
    }

    @Override
    public double getStayCost(long id) {
        Client client = getById(id);
        if (client == null) {
            return 0.0;
        }
        return client.getStayCost();
    }

    @Override
    public boolean checkInApartment(long clientId, long apartmentId, int stayDuration) {
        Client client = getById(clientId);
        Apartment apartment = apartmentDao.getById(apartmentId)
                .orElseThrow(() -> new RuntimeException("Apartment with this id doesn't exist. Id: " + apartmentId));
        if (ClientStatus.CHECKED_IN == client.getStatus()) {
            throw new RuntimeException("This client is already checked in. Apartment id: " + client.getApartment().getId());
        }
        if (ApartmentStatus.AVAILABLE == apartment.getStatus()
                && apartment.getCapacity() >= client.getQuantityOfPeople()) {
            checkInProcedure(stayDuration, client, apartment);
            return true;
        } else {
            throw new RuntimeException("No available apartment in the hotel");
        }
    }

    @Override
    public boolean checkInAnyFreeApartment(long clientId, int stayDuration) {
        Client client = getById(clientId);
        List<Apartment> apartments = apartmentDao.getAll();
        if (ClientStatus.CHECKED_IN == client.getStatus()) {
            throw new RuntimeException("This client is already checked in. Apartment id: " + client.getApartment().getId());
        }
        Optional<Apartment> availableApartment = apartments.stream()
                .filter(apartment -> apartment.getCapacity() >= client.getQuantityOfPeople())
                .filter(apartment -> ApartmentStatus.AVAILABLE == apartment.getStatus())
                .findFirst();
        if (availableApartment.isPresent()) {
            Apartment apartment = availableApartment.get();
            checkInProcedure(stayDuration, client, apartment);
            return true;
        } else {
            throw new RuntimeException("No available apartment in the hotel");
        }
    }

    private void checkInProcedure(int stayDuration, Client client, Apartment apartment) {
        client.setApartment(apartment);
        client.setStatus(ClientStatus.CHECKED_IN);
        client.setCheckOutDate(LocalDateTime.now().plusDays(stayDuration));
        client.setCheckInDate(LocalDateTime.now());
        apartment.setStatus(ApartmentStatus.UNAVAILABLE);
        client.setStayCost(client.getStayCost() + (apartment.getPrice() * stayDuration));
        clientDao.update(client);
        apartmentDao.update(apartment);
    }

    @Override
    public boolean checkOutApartment(long clientId) {
        Client client = getById(clientId);
        Apartment apartment = client.getApartment();
        if (apartment != null) {
            client.setApartment(new Apartment());
            client.setStatus(ClientStatus.CHECKED_OUT);
            client.setPerks(new ArrayList<>());
            client.setStayCost(0.0);
            apartment.setStatus(ApartmentStatus.AVAILABLE);
            clientDao.update(client);
            apartmentDao.update(apartment);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Perk addPerk(long clientId, long perkId) {
        Client client = getById(clientId);
        Perk perk = perkDao.getById(perkId)
                .orElseThrow(() -> new RuntimeException("Perk with this id doesn't exist. Id: " + perkId));
        List<Perk> clientPerks = client.getPerks();
        clientPerks.add(perk);
        client.setPerks(clientPerks);
        client.setStayCost(client.getStayCost() + perk.getPrice());
        clientDao.update(client);
        return perk;
    }

    @Override
    public List<Perk> getAllPerks(long clientId) {
        Client client = getById(clientId);
        if (client != null) {
            return client.getPerks();
        }
        return List.of();
    }

    @Override
    public List<Client> sortByName() {
        List<Client> sortedByName = new ArrayList<>(clientDao.getAll());
        sortedByName.sort(Comparator.comparing(Client::getName));
        return sortedByName;
    }

    @Override
    public List<Client> sortByCheckOutDate() {
        List<Client> sortedByCheckOutDate = new ArrayList<>(clientDao.getAll());
        return sortedByCheckOutDate.stream()
                .filter(client -> client.getStatus() != ClientStatus.NEW)
                .sorted(Comparator.comparing(Client::getCheckOutDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> sortByStatus() {
        List<Client> sortedByStatus = new ArrayList<>(clientDao.getAll());
        sortedByStatus.sort(Comparator.comparing(Client::getStatus));
        return sortedByStatus;
    }
}
