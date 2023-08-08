package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;

import java.util.List;
import java.util.Optional;

public interface ApartmentDao {
    Optional<Apartment> getById(long id);

    List<Apartment> getAll();

    Apartment save(Apartment apartment);

    Optional<Apartment> update(Apartment apartment);

    boolean remove(long id);
}
