package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.factory.HotelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OnDiskApartmentDaoImpl implements ApartmentDao {
    private final OnDiskJsonHandler onDiskJsonHandler;
    public OnDiskApartmentDaoImpl(HotelFactory hotelFactory) {
        this.onDiskJsonHandler = new OnDiskJsonHandler(hotelFactory);
    }

    @Override
    public Optional<Apartment> getById(long id) {
        return onDiskJsonHandler.load().getApartmentsList()
                .stream()
                .filter(apartment -> apartment.getId() == id)
                .findFirst();
    }

    @Override
    public List<Apartment> getAll() {
        return onDiskJsonHandler.load().getApartmentsList();
    }

    @Override
    public Apartment save(Apartment apartment) {
        var stateEntity = onDiskJsonHandler.load();
        var apartments = stateEntity.getApartmentsList();
        var copy = new ArrayList<>(apartments);
        copy.add(apartment);

        onDiskJsonHandler.save(stateEntity.addApartmentList(copy));
        return apartment;
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        var stateEntity = onDiskJsonHandler.load();
        var existingApartment= stateEntity.getApartmentsList()
                .stream()
                .filter(apartment1 -> apartment1.getId() == apartment.getId())
                .findFirst();

        existingApartment.ifPresent(apt -> {
            if (apartment.getPrice() != 0.0) {
                apt.setPrice(apartment.getPrice());
            }
            if (apartment.getCapacity() != 0) {
                apt.setCapacity(apartment.getCapacity());
            }
            if (apartment.getStatus() != null) {
                apt.setStatus(apartment.getStatus());
            }
        });

        onDiskJsonHandler.save(stateEntity);
        return existingApartment;
    }

    @Override
    public boolean remove(long id) {
        var entityState = onDiskJsonHandler.load();
        var answer =  entityState.getApartmentsList()
                .removeIf(apartment -> apartment.getId() == id);

        onDiskJsonHandler.save(entityState);
        return answer;
    }
}
