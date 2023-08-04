package com.andersenlab.service;

import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.List;

public interface ClientService {
    Client getById(long id);

    List<Client> getAll();

    Client save(String name, int quantityOfPeople);

    double getStayCost(long id);

    Client checkInApartment(long clientId, int stayDuration, long apartmentId);
    Client checkInAnyFreeApartment(long clientId, int stayDuration);

    double checkOutApartment(long clientId);

    Perk addPerk(long clientId, long perkId);

    List<Perk> getAllPerks(long clientId);

    List<Client> getSorted(ClientSortType type);

    enum ClientSortType {
        ID, NAME, CHECK_OUT_DATE, STATUS
    }
}
