package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClientDao {
    private List<Client> clients;

    public ClientDao() {
        this.clients = new ArrayList<>();
    }
    //Сначала добавить Клиента в базу данных клиентов, и только потом на нем вызывать Чекин
    public Client add(Client client) {
        clients.add(client);
        return clients.get(clients.size() - 1);
    }
    public boolean checkIn(Client client, Apartment apartment) {
        boolean successfulCheckIn = false;
        for (Client guest : clients) {
            if (guest.equals(client)) {
                client.setApartment(apartment);
                client.setStatus(true);
                successfulCheckIn = true;
            }
        } return successfulCheckIn;
    }
    public boolean checkOut (Client client) {
        boolean successfulCheckOut = false;
        for (Client guest : clients) {
            if (guest.equals(client)) {
                client.setApartment(null);
                client.setStatus(false);
                successfulCheckOut = true;
            }
        } return successfulCheckOut;
    }
    public List<Client> listByName() {
        List<Client> sortedByName = new ArrayList<>(clients);
        sortedByName.sort(Comparator.comparing(Client::getName));
        return sortedByName;
    }
    public List<Client> listByCheckOutDay() {
        List<Client> sortedByCheckOutDay = new ArrayList<>(clients);
        sortedByCheckOutDay.sort(Comparator.comparing(Client::getCheckOutDay));
        return sortedByCheckOutDay;
    }
    public List<Client> listByStatus() {
        List<Client> sortedByStatus = new ArrayList<>(clients);
        sortedByStatus.sort(Comparator.comparing(Client::hasStatus));
        return sortedByStatus;
    }
    public Perk addNewPerk(Client client, Perk perk) {
        Perk result = null;
        for (Client guest : clients) {
            if (guest.equals(client)) {
                List<Perk> guestPerks = guest.getPerks();
                guestPerks.add(perk);
                guest.setPerks(guestPerks);
                result = perk;
            }
        } return result;
    }
    public double stayCost(Client client) {
        double result = 0.0;
        for (Client guest : clients) {
            if (guest.equals(client)) {
                result += guest.getApartment().getPrice();
                List<Perk> guestPerks = guest.getPerks();
                for (Perk perk : guestPerks) {
                    result += perk.getPrice();
                }
            }
        }
        return result;
    }

}
