package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;

import java.util.List;
import java.util.Optional;

public class PerkDaoOnDiskImpl implements PerkDao {
    private final HotelFactory hotelFactory = new HotelFactory();
    private final JsonHandlerImp jsonHandler = new JsonHandlerImp(hotelFactory);

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
        var perks = jsonHandler.load().perksList();
        perks.add(perk);

        jsonHandler.save();
        return perk;
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        var existingPerk = jsonHandler.load().perksList()
                .stream()
                .filter(perk1 -> perk1.getId() == perk.getId())
                .findFirst();

        existingPerk.ifPresent(updPerk -> {
            if (perk.getName() != null) {
                updPerk.setName(perk.getName());
            }
            updPerk.setPrice(perk.getPrice());
        });

        jsonHandler.save();
        return existingPerk;
    }

    @Override
    public boolean remove(long id) {
        var answer = jsonHandler.load().perksList()
                .removeIf(perk -> perk.getId() == id);

        jsonHandler.save();
        return answer;
    }
}
