package com.andersenlab.dao.hibernateImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;

import java.util.List;
import java.util.Optional;

public class HibernateApartmentDaoImpl implements ApartmentDao {
    @Override
    public Optional<Apartment> getById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Apartment> getAll() {
        return null;
    }

    @Override
    public Apartment save(Apartment apartment) {
        return null;
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        return Optional.empty();
    }

    @Override
    public boolean remove(long id) {
        return false;
    }

    @Override
    public List<Apartment> getSortedBy(ApartmentSortType type) {
        return null;
    }
}
