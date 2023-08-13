package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Optional;

public class OnDiskPerkDaoImpl implements PerkDao {
    private final OnDiskJsonHandler onDiskJsonHandler;
    public OnDiskPerkDaoImpl(HotelFactory hotelFactory) {
        this.onDiskJsonHandler = new OnDiskJsonHandlerImp(hotelFactory);
    }

    @Override
    public Optional<Perk> getById(long id) {
        return onDiskJsonHandler.load().perksList()
                .stream()
                .filter(perk -> perk.getId() == id)
                .findFirst();
    }

    @Override
    public List<Perk> getAll() {
        return onDiskJsonHandler.load().perksList();
    }

    @Override
    public Perk save(Perk perk) {
        var stateEntity = onDiskJsonHandler.load();
        var perks = stateEntity.perksList();
        perks.add(perk);

        onDiskJsonHandler.save(stateEntity);
        return perk;
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        var stateEntity = onDiskJsonHandler.load();
        var existingPerk = stateEntity.perksList()
                .stream()
                .filter(perk1 -> perk1.getId() == perk.getId())
                .findFirst();

        existingPerk.ifPresent(updPerk -> {
            if (perk.getName() != null) {
                updPerk.setName(perk.getName());
            }
            updPerk.setPrice(perk.getPrice());
        });

        onDiskJsonHandler.save(stateEntity);
        return existingPerk;
    }

    @Override
    public boolean remove(long id) {
        var stateEntity = onDiskJsonHandler.load();
        var answer = stateEntity.perksList()
                .removeIf(perk -> perk.getId() == id);

        onDiskJsonHandler.save(stateEntity);
        return answer;
    }
}
