package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.util.IdGenerator;

import java.time.LocalDateTime;

public class ClientService {
    private final ClientDao clientDao;
    private final ApartmentDao apartmentDao;

    private final PerkDao perkDao;

    public ClientService(ClientDao clientDao, ApartmentDao apartmentDao, PerkDao perkDao) {
        this.clientDao = clientDao;
        this.apartmentDao = apartmentDao;
        this.perkDao = perkDao;
    }

    //Передав long щоб консоль вивела: "ВИ успішно зареєструвались... Вас ID: 'id'"
    public long createAndAddNewClient(String name) {
        return clientDao.addClient(new Client(IdGenerator.generateClientId(), name));
    }

    public Client getClient(long id) {
        return clientDao.getClientById(id);
    }

    public double getCurrentPriceToPay(long id) {
        Client client = getClient(id);
        if (client != null)
            return client.getCurrentPriceToPay();
    return 0;
    }


    //Методи які взаємодіють з матодами інших дао


    public boolean checkInApartment(long clientId, long apartmentId) {
        Client client = getClient(clientId);
        Apartment apartment = apartmentDao.get(apartmentId);

        if (client != null && apartment != null)
            if (apartment.getApartmentStatus() == ApartmentStatus.AVAILABLE) {
                client.setApartment(apartment);
                client.setLives(true);
                client.setCheckOutDate(LocalDateTime.now().plusDays(7));
                client.setCurrentPriceToPay(client.getCurrentPriceToPay() + apartment.getApartmentPrice());
                apartment.setApartmentStatus(ApartmentStatus.UNAVAILABLE);
                return true;
            }
        return false;
    }

    public boolean checkOutApartment(long clientId) {
        Client client = getClient(clientId);

        if (client != null) {
            Apartment apartment = client.getApartment();

            if (apartment != null) {
                client.setApartment(null);
                client.setLives(false);
                client.setCheckOutDate(null);
                apartment.setApartmentStatus(ApartmentStatus.AVAILABLE);
                return true;
            }
        }
        return false;
    }

    public boolean orderPerks(long clientId, long perkId) {
        Client client = getClient(clientId);
        Perk perk = perkDao.get(perkId);

        if (perk != null && client != null) {
            client.addPerk(perk);
            client.setCurrentPriceToPay(client.getCurrentPriceToPay() + perk.getPrice());

            return true;
        }
        return false;
    }

}
