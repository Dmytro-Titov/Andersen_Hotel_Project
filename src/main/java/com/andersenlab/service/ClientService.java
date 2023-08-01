package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.util.IdGenerator;

import java.time.LocalDateTime;

public class ClientService {
    private final ClientDao clientDao = ClientDao.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    private final PerkDao perkDao = PerkDao.getInstance();

    public Long createAndAddNewClient(String name) {
        return clientDao.addClient(new Client(IdGenerator.generateClientId(), name));
    }

    public Client getClient(Long id) {
        return clientDao.getClientById(id);
    }

    public void getCurrentPriceToPay(Long id) {
        Client client = getClient(id);
        if(client != null)
            System.out.println("Client " + client.getName() + " must to pay: " + client.getCurrentPriceToPay());
    }

    public void checkInApartment(Long clientId, Long apartmentId) {
        Client client = getClient(clientId);
        Apartment apartment = apartmentDao.getApartmentById(apartmentId);

        if (client != null && apartment != null)
            if (apartment.isAvailable()) {
                client.setApartment(apartment);
                client.setLives(true);
                client.setCheckOutDate(LocalDateTime.now().plusDays(7));
                client.setCurrentPriceToPay(client.getCurrentPriceToPay() + apartment.getPrice());
                apartment.setAvailability(false);
                System.out.println("You have successfully checked into the hotel, your apartment is under the number" + apartmentId);
            }
    }

    public void checkOutApartment(Long clientId) {
        Client client = getClient(clientId);

        if (client != null) {
            Apartment apartment = client.getApartment();

            if (apartment != null) {
                client.setApartment(null);
                client.setLives(false);
                client.setCheckOutDate(null);
                apartment.setAvailability(true);
                System.out.println("You have successfully checked out the hotel!");
            }
        }
    }

    public void orderPerks(Long clientId, Long perkId) {
        Client client = getClient(clientId);
        Perk perk = perkDao.getPerkById(perkId);

        if (perk != null && client != null) {
            client.addPerk(perk);
            client.setCurrentPriceToPay(client.getCurrentPriceToPay() + perk.getPrice());

            System.out.println("You have successfully ordered a perk!");
        }
    }

}
