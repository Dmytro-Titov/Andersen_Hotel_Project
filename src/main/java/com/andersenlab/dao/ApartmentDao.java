package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;

import java.util.List;

public interface ApartmentDao {
    Apartment getById(long id);

    List<Apartment> getAll();

    void save(Apartment apartment);

    void update(Apartment apartment);

    void remove(long id);
}
