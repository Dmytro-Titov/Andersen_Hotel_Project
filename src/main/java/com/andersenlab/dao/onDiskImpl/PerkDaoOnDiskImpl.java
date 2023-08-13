package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Optional;

public class PerkDaoOnDiskImpl implements PerkDao {
    private final JsonHandler jsonHandler;
    public PerkDaoOnDiskImpl(HotelFactory hotelFactory) {
        this.jsonHandler = new JsonHandlerImp(hotelFactory);
    }

    @Override
    public Optional<Perk> getById(long id) {
        return jsonHandler.load().perksList()
                .stream()
                .filter(perk -> perk.getId() == id)
                .findFirst();
    }

    @Override
    public List<Perk> getAll() {
        return jsonHandler.load().perksList();
    }

    @Override
    public Perk save(Perk perk) {
        var stateEntity = jsonHandler.load();
        var perks = stateEntity.perksList();
        perks.add(perk);

        jsonHandler.save(stateEntity);
        return perk;
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        var stateEntity = jsonHandler.load();
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

        jsonHandler.save(stateEntity);
        return existingPerk;
    }

    @Override
    public boolean remove(long id) {
        var stateEntity = jsonHandler.load();
        var answer = stateEntity.perksList()
                .removeIf(perk -> perk.getId() == id);

        jsonHandler.save(stateEntity);
        return answer;
    }
}
