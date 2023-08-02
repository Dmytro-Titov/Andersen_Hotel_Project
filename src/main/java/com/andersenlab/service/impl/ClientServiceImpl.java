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
        return clientDao.getById(id);
    }

    @Override
    public void save(String name) {
        Client client = new Client(IdGenerator.generateClientId(), name);
        clientDao.save(client);
    }

    @Override
    public double getStayCost(long id) {
        double result = 0.0;
        Client client = getById(id);
        if (client != null) {
            if (client.getApartment() != null) {
                result = client.getApartment().getPrice();
            }
            if (!client.getPerks().isEmpty()) {
                for (Perk perk : client.getPerks()) {
                    result += perk.getPrice();
                }
            }
        }
        return result;
    }

    @Override
    public boolean checkInApartment(long clientId, long apartmentId, int stayDuration) {
        Client client = getById(clientId);
        Apartment apartment = apartmentDao.getById(apartmentId);
        if (client != null && apartment != null)
            if (apartment.getStatus() == ApartmentStatus.AVAILABLE) {
                client.setApartment(apartment);
                client.setStatus(ClientStatus.CHECKED_IN);
                client.setCheckOutDate(LocalDateTime.now().plusDays(stayDuration));
                apartment.setStatus(ApartmentStatus.UNAVAILABLE);
                clientDao.update(client);
                apartmentDao.update(apartment);
                return true;
            }
        return false;
    }

    @Override
    public boolean checkOutApartment(long clientId) {
        Client client = getById(clientId);
        if (client != null) {
            Apartment apartment = client.getApartment();
            if (apartment != null) {
                client.setApartment(null);
                client.setStatus(ClientStatus.CHECKED_OUT);
                client.setCheckOutDate(LocalDateTime.now());
                client.setPerks(new ArrayList<>());
                apartment.setStatus(ApartmentStatus.AVAILABLE);
                clientDao.update(client);
                apartmentDao.update(apartment);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean addPerk(long clientId, long perkId) {
        Client client = getById(clientId);
        Perk perk = perkDao.getById(perkId);
        if (perk != null && client != null) {
            List<Perk> clientPerks = client.getPerks();
            clientPerks.add(perk);
            client.setPerks(clientPerks);
            clientDao.update(client);
            return true;
        }
        return false;
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
                .sorted(Comparator.comparing(Client::getStatus))
                .collect(Collectors.toList());
    }

    @Override
    public List<Client> sortByStatus() {
        List<Client> sortedByStatus = new ArrayList<>(clientDao.getAll());
        sortedByStatus.sort(Comparator.comparing(Client::getStatus));
        return sortedByStatus;
    }
}
