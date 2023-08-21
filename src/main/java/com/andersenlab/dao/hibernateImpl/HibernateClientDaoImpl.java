package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.entity.Client;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateClientDaoImpl implements ClientDao {
    private final SessionFactory sessionFactory;

    public HibernateClientDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Client> getById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(client);
        }
    }

    @Override
    public List<Client> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Client> apartments = session.createQuery("FROM Client ORDER BY id").getResultList();
            session.getTransaction().commit();
            return apartments;
        }
    }

    @Override
    public Client save(Client client) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
            return client;
        }
    }

    @Override
    public Optional<Client> update(Client client) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Client> existingClient = Optional.ofNullable(session.get(Client.class, client.getId()));
            existingClient.ifPresent(clnt -> updateClientFields(clnt, client));
            session.getTransaction().commit();
            return existingClient;
        }
    }

    @Override
    public boolean remove(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Client> existingCLient = Optional.ofNullable(session.get(Client.class, id));
            existingCLient.ifPresent(clnt -> deleteClient(clnt, session));
            return existingCLient.isPresent();
        }
    }

    @Override
    public List<Client> getSortedBy(ClientSortType type) {
        return switch (type) {
            case ID -> getAll();
            case NAME -> sortBy("FROM Client ORDER BY name");
            case CHECK_OUT_DATE -> sortBy("FROM Apartment ORDER BY checkOutDate");
            case STATUS -> sortBy("FROM Apartment ORDER BY status");
        };
    }

    private List<Client> sortBy(String query) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Client> clients = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return clients;
        }
    }

    private void updateClientFields(Client existingClient, Client updatedClient) {
        existingClient.setName(updatedClient.getName());
        existingClient.setCheckOutDate(updatedClient.getCheckOutDate());
        existingClient.setCheckInDate(updatedClient.getCheckInDate());
        existingClient.setStatus(updatedClient.getStatus());
        existingClient.setApartment(updatedClient.getApartment());
        existingClient.setPerks(updatedClient.getPerks());
        existingClient.setStayCost(updatedClient.getStayCost());
    }

    private void deleteClient(Client client, Session session) {
        session.delete(client);
        session.getTransaction().commit();
    }
}
