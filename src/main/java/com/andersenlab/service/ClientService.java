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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * ClientService(ClientDAO, ApartmentDAO, PerkDAO)
 *
 *    long          save( String clientName ) Сохраняет клиента в Базу, возвращает его ID
 *    Client        getClient( long id )   Возвращает клиента
 *    double        getCurrentPriceToPay( long id ) Возвращает сумму которую должен оплатить клиент
 *    List<Client>  sortByName() Возвращает список клиентов отсортированый по имени
 *    List<Client>  sortByCheckOutDate() Возвращает список клиентов отсортированый по дате выезда
 *    List<Client>  sortByStatus() Возвращает список клиентов отсортированый по дате выезда
 *    boolean       checkInApartment( long clientId, long apartmentId ) Возвращает TRUE если клиент успешно заселился в номер отеля
 *    boolean       checkOutApartment( long clientId ) Возвращает TRUE если клиент успешно сйехал с номера отеля
 *    boolean       orderPerks( long clientId, long perkId ) Возвращает TRUE если клиент успешно заказал услугу
 *    List<Perk>    showClientsPerks( long clientId ) Возвращает списуг услуг или пустой список
 *
 */




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
    public long save(String name) {
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

    public List<Client> sortByName() {
        return clientDao.getAllClients().stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());

    }

    public List<Client> sortByCheckOutDate() {
        return clientDao.getAllClients().stream()
                .sorted(Comparator.comparing(Client::getCheckOutDate))
                .collect(Collectors.toList());

    }

    public List<Client> sortByStatus() {
        return clientDao.getAllClients().stream()
                .sorted(Comparator.comparing(Client::isLives))
                .collect(Collectors.toList());

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

    public List<Perk> showClientsPerks(long clientId) {
        Client client = getClient(clientId);
        if (client != null) {
            return client.getPerks();
        }
        return List.of();
    }

}
