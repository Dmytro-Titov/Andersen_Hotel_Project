package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Optional;

public class ApartmentDaoOnDiskImpl implements ApartmentDao {
    private final JsonHandler jsonHandler = new JsonHandlerImp();

    public ApartmentDaoOnDiskImpl() {
    }

    @Override
    public Optional<Apartment> getById(long id) {
        return jsonHandler.load().apartmentsList()
                .stream()
                .filter(apartment -> apartment.getId() == id)
                .findFirst();
    }

    @Override
    public List<Apartment> getAll() {
        return jsonHandler.load().apartmentsList();
    }

    @Override
    public Apartment save(Apartment apartment) {
        var stateEntity = jsonHandler.load();
        var apartments = stateEntity.apartmentsList();
        apartments.add(apartment);

        jsonHandler.save(stateEntity);
        return apartment;
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        var stateEntity = jsonHandler.load();
        var existingApartment= stateEntity.apartmentsList()
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

        jsonHandler.save(stateEntity);
        return existingApartment;
    }

    @Override
    public boolean remove(long id) {
        var entityState = jsonHandler.load();
        var answer =  entityState.apartmentsList()
                .removeIf(apartment -> apartment.getId() == id);

        jsonHandler.save(entityState);
        return answer;
    }
}
