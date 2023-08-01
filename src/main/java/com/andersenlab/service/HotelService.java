package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.List;

public class HotelService {
    private final ClientDao clientDao;
    private final ApartmentDao apartmentDao;
    private final PerkDao perkDao;

    public HotelService(ClientDao clientDao, ApartmentDao apartmentDao, PerkDao perkDao) {
        this.clientDao = clientDao;
        this.apartmentDao = apartmentDao;
        this.perkDao = perkDao;
    }
    public Client addNewClient(Client client) {
        return clientDao.add(client);
    }
    public boolean checkIn(Client client, Apartment apartment) {
        return clientDao.checkIn(client, apartment) && apartmentDao.bookApartment(apartment);

    }
    public boolean checkOut(Client client, Apartment apartment) {
        return clientDao.checkOut(client) && apartmentDao.unBookApartment(apartment);
    }
    public boolean bookApartment(Apartment apartment) {
        return apartmentDao.bookApartment(apartment);
    }
    public Apartment changeApartmentPrice(Apartment apartment, double newPrice) {
        return apartmentDao.changeApartmentPrice(apartment, newPrice);
    }
    public Apartment addApartment(Apartment apartment) {
        return apartmentDao.add(apartment);
    }
    public List<Apartment> listByApartmentPrice() {
        return apartmentDao.listByPrice();
    }
    public List<Apartment> listByApartmentCapacity() {
        return apartmentDao.listByCapacity();
    }
    public List<Apartment> listByApartmentAvailability() {
        return apartmentDao.listByAvailability();
    }
    public List<Client> listByClientName() {
        return clientDao.listByName();
    }
    public List<Client> listByClientCheckOutDay() {
        return clientDao.listByCheckOutDay();
    }
    public List<Client> listByClientStatus() {
        return clientDao.listByStatus();
    }
    public Perk addPerk(Perk perk) {
        return perkDao.add(perk);
    }
    public Perk changePerkPrice(Perk perk, double newPrice) {
       return perkDao.changePerkPrice(perk, newPrice);
    }
    public Perk addNewPerkToClient(Client client, Perk perk) {
       return clientDao.addNewPerk(client, perk);
    }
    public List<Perk> listByPerkName() {
        return perkDao.listByName();
    }
    public List<Perk> listByPerkPrice() {
        return perkDao.listByPrice();
    }
    public double stayCost(Client client) {
        return clientDao.stayCost(client);
    }


}
