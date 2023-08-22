package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernateApartmentDaoImpl implements ApartmentDao {
    private final SessionFactory sessionFactory;

    public HibernateApartmentDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Apartment> getById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Apartment apartment = session.get(Apartment.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(apartment);
        }
    }

    @Override
    public List<Apartment> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Apartment> apartments = session.createQuery("FROM Apartment ORDER BY id").getResultList();
            session.getTransaction().commit();
            return apartments;
        }
    }

    @Override
    public Apartment save(Apartment apartment) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(apartment);
            session.getTransaction().commit();
            return apartment;
        }
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Apartment> existingApartment = Optional.ofNullable(session.get(Apartment.class, apartment.getId()));
            existingApartment.ifPresent(apt -> updateApartmentFields(apt, apartment));
            session.getTransaction().commit();
            return existingApartment;
        }
    }

    @Override
    public boolean remove(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Apartment> existingApartment = Optional.ofNullable(session.get(Apartment.class, id));
            existingApartment.ifPresent(apartment -> deleteApartment(apartment, session));
            return existingApartment.isPresent();
        }
    }

    @Override
    public List<Apartment> getSortedBy(ApartmentSortType type) {
        return switch (type) {
            case ID -> getAll();
            case PRICE -> sortBy("FROM Apartment ORDER BY price");
            case CAPACITY -> sortBy("FROM Apartment ORDER BY capacity");
            case STATUS -> sortBy("FROM Apartment ORDER BY status");
        };
    }

    private List<Apartment> sortBy(String query) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Apartment> apartments = session.createQuery(query).getResultList();
            session.getTransaction().commit();
            return apartments;
        }
    }

    private void updateApartmentFields(Apartment existingApartment, Apartment updatedApartment) {
        if (updatedApartment.getPrice() != 0.0) {
            existingApartment.setPrice(updatedApartment.getPrice());
        }
        if (updatedApartment.getCapacity() != 0) {
            existingApartment.setCapacity(updatedApartment.getCapacity());
        }
        if (updatedApartment.getStatus() != null) {
            existingApartment.setStatus(updatedApartment.getStatus());
        }
    }

    private void deleteApartment(Apartment apartment, Session session) {
        session.delete(apartment);
        session.getTransaction().commit();
    }

    @Override
    public void cleanTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Apartment").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
