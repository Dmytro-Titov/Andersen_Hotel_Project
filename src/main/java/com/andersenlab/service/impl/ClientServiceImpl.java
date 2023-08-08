package com.andersenlab.service.impl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.*;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientServiceImpl implements ClientService {

    private final ClientDao clientDao;
    private final ApartmentService apartmentService;
    private final PerkService perkService;

    public ClientServiceImpl(ClientDao clientDao, HotelFactory hotelFactory) {
        this.clientDao = clientDao;
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();
    }

    @Override
    public Client getById(long id) {
        return clientDao.getById(id)
                .orElseThrow(() -> new RuntimeException("Client with this id doesn't exist. Id: " + id));
    }

    @Override
    public List<Client> getAll() {
        return clientDao.getAll();
    }

    @Override
    public Client save(String name, int quantityOfPeople) {
        return clientDao.save(new Client(IdGenerator.generateClientId(), name, quantityOfPeople));
    }

    @Override
    public Client update(Client client) {
        return clientDao.update(client)
                .orElseThrow(() -> new RuntimeException("Client with this id doesn't exist. Id: " + client.getId()));
    }

    @Override
    public double getStayCost(long id) {
        return getById(id).getStayCost();
    }

    @Override
    public Client checkInApartment(long clientId, int stayDuration, long apartmentId) {
        if (apartmentId == 0) {
            return checkInAnyFreeApartment(clientId, stayDuration);
        }
        Client client = getById(clientId);
        Apartment apartment = apartmentService.getById(apartmentId);
        if (ClientStatus.CHECKED_IN == client.getStatus()) {
            throw new RuntimeException("This client is already checked in. Apartment id: "
                    + client.getApartment().getId());
        }
        if (ApartmentStatus.AVAILABLE == apartment.getStatus()
                && apartment.getCapacity() >= client.getQuantityOfPeople()) {
            checkInProcedure(stayDuration, client, apartment);
            return client;
        } else {
            throw new RuntimeException("No available apartment in the hotel");
        }
    }

    public Client checkInAnyFreeApartment(long clientId, int stayDuration) {
        Client client = getById(clientId);
        List<Apartment> apartments = apartmentService.getAll();
        if (ClientStatus.CHECKED_IN == client.getStatus()) {
            throw new RuntimeException("This client is already checked in. Apartment id: "
                    + client.getApartment().getId());
        }
        Optional<Apartment> availableApartment = apartments.stream()
                .filter(apartment -> apartment.getCapacity() >= client.getQuantityOfPeople())
                .filter(apartment -> ApartmentStatus.AVAILABLE == apartment.getStatus())
                .findFirst();
        if (availableApartment.isPresent()) {
            Apartment apartment = availableApartment.get();
            checkInProcedure(stayDuration, client, apartment);
            return client;
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
        update(client);
        apartmentService.update(apartment);
    }

    @Override
    public double checkOutApartment(long clientId) {
        Client client = getById(clientId);
        Apartment apartment = client.getApartment();
        if (apartment != null) {
            double stayCost = client.getStayCost();
            client.setApartment(null);
            client.setStatus(ClientStatus.CHECKED_OUT);
            client.setPerks(new ArrayList<>());
            client.setStayCost(0.0);
            apartment.setStatus(ApartmentStatus.AVAILABLE);
            update(client);
            apartmentService.update(apartment);
            return stayCost;
        } else {
            throw new RuntimeException("This client isn't checked in in any apartment yet! Id: " + client.getId());
        }
    }

    @Override
    public Perk addPerk(long clientId, long perkId) {
        Client client = getById(clientId);
        if (ClientStatus.CHECKED_OUT == client.getStatus() || ClientStatus.NEW == client.getStatus()) {
            throw new RuntimeException("This client is not checked in, you cannot add him/her new perks");
        }
        Perk perk = perkService.getById(perkId);
        List<Perk> clientPerks = client.getPerks();
        clientPerks.add(perk);
        client.setPerks(clientPerks);
        client.setStayCost(client.getStayCost() + perk.getPrice());
        update(client);
        return perk;
    }

    @Override
    public List<Perk> getAllPerks(long clientId) {
        return getById(clientId).getPerks();
    }

    @Override
    public List<Client> getSorted(ClientSortType type) {
        return switch (type) {
            case ID -> getAll();
            case CHECK_OUT_DATE -> sortByCheckOutDate();
            case NAME -> sortByName();
            case STATUS -> sortByStatus();
        };
    }

    private List<Client> sortByName() {
        List<Client> sortedByName = new ArrayList<>(getAll());
        sortedByName.sort(Comparator.comparing(Client::getName));
        return sortedByName;
    }

    private List<Client> sortByCheckOutDate() {
        List<Client> sortedByCheckOutDate = new ArrayList<>(getAll());
        return sortedByCheckOutDate.stream()
                .filter(client -> client.getStatus() != ClientStatus.NEW)
                .sorted(Comparator.comparing(Client::getCheckOutDate))
                .collect(Collectors.toList());
    }

    private List<Client> sortByStatus() {
        List<Client> sortedByStatus = new ArrayList<>(getAll());
        sortedByStatus.sort(Comparator.comparing(Client::getStatus));
        return sortedByStatus;
    }
}
