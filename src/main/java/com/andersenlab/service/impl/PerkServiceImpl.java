package com.andersenlab.service.impl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.exceptions.IdDoesNotExistException;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.EntityValidityCheck;
import com.andersenlab.util.IdGenerator;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class PerkServiceImpl implements PerkService {
    private final PerkDao perkDao;

    public PerkServiceImpl(PerkDao perkDao, HotelFactory hotelFactory) {
        this.perkDao = perkDao;
    }

    @Override
    public Perk getById(long id) {
        return perkDao.getById(id)
                .orElseThrow(() -> new IdDoesNotExistException("Perk with this id doesn't exist. Id: " + id));
    }

    @Override
    public Perk save(String name, double price) {
        EntityValidityCheck.perkPriceCheck(price);
        return perkDao.save(new Perk(IdGenerator.generatePerkId(), name, price));
    }

    public void save(List<Perk> perks) {
        perks.forEach(perk -> {
            perkDao.save(perk);
            IdGenerator.generatePerkId();
        });
    }

    @Override
    public Perk update(Perk perk) {
        return perkDao.update(perk)
                .orElseThrow(() -> new IdDoesNotExistException("Perk with this id doesn't exist. Id: " + perk.getId()));
    }

    @Override
    public Perk changePrice(long id, double price) {
        EntityValidityCheck.perkPriceCheck(price);
        return update(new Perk(id, price));
    }

    @Override
    public List<Perk> getAll() {
        return perkDao.getAll();
    }

    @Override
    public List<Perk> getSorted(PerkSortType type) {
        return switch (type) {
            case ID -> getAll();
            case NAME -> sortBy(Perk::getName);
            case PRICE -> sortBy(Perk::getPrice);
        };
    }

    private List<Perk> sortBy(Function<Perk, Comparable> extractor) {
        return getAll().stream()
                .sorted(Comparator.comparing(extractor))
                .toList();
    }
}
