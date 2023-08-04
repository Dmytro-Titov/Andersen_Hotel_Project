package com.andersenlab.service;

import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.List;

public interface ClientService {
    Client getById(long id);

    Client save(String name, int quantityOfPeople);

    double getStayCost(long id);

    boolean checkInApartment(long clientId, long apartmentId, int stayDuration);
    boolean checkInAnyFreeApartment(long clientId, int stayDuration);

    boolean checkOutApartment(long clientId);

    Perk addPerk(long clientId, long perkId);

    List<Perk> getAllPerks(long clientId);

    List<Client> sortByName();

    List<Client> sortByCheckOutDate();

    List<Client> sortByStatus();

}
