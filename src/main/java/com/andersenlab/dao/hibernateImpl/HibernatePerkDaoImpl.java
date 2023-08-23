package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class HibernatePerkDaoImpl implements PerkDao {
    private final SessionFactory sessionFactory;

    public HibernatePerkDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Perk> getById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Perk perk = session.get(Perk.class, id);
            session.getTransaction().commit();
            return Optional.ofNullable(perk);
        }
    }

    @Override
    public List<Perk> getAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Perk> perks = session.createQuery("FROM Perk ORDER BY id").getResultList();
            session.getTransaction().commit();
            return perks;
        }
    }

    @Override
    public Perk save(Perk perk) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.save(perk);
            session.getTransaction().commit();
            return perk;
        }
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Perk> existingPerk = Optional.ofNullable(session.get(Perk.class, perk.getId()));
            existingPerk.ifPresent(prk -> updatePerkFields(prk, perk));
            session.getTransaction().commit();
            return existingPerk;
        }
    }

    @Override
    public boolean remove(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Perk> existingPerk = Optional.ofNullable(session.get(Perk.class, id));
            existingPerk.ifPresent(perk -> deletePerk(perk, session));
            return existingPerk.isPresent();
        }
    }

    @Override
    public List<Perk> getSortedBy(PerkSortType type) {
        return switch (type) {
            case ID -> getAll();
            case NAME -> sortBy("name");
            case PRICE -> sortBy("price");
        };
    }

    private List<Perk> sortBy(String parameter) {
        String getAllQuery = "FROM Perk ORDER BY " + parameter;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            List<Perk> perks = session.createQuery(getAllQuery).getResultList();
            session.getTransaction().commit();
            return perks;
        }
    }

    private void updatePerkFields(Perk existingPerk, Perk updatedPerk) {
        if (updatedPerk.getName() != null) {
            existingPerk.setName(updatedPerk.getName());
        }
        existingPerk.setPrice(updatedPerk.getPrice());
    }

    private void deletePerk(Perk perk, Session session) {
        session.delete(perk);
        session.getTransaction().commit();
    }
}
