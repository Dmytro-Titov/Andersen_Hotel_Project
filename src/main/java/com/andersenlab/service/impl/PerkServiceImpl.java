package com.andersenlab.service.impl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.EntityValidityCheck;
import com.andersenlab.util.IdGenerator;

import java.util.Comparator;
import java.util.List;

public class PerkServiceImpl implements PerkService {
    private final PerkDao perkDao;

    public PerkServiceImpl(PerkDao perkDao, HotelFactory hotelFactory) {
        this.perkDao = perkDao;
    }

    @Override
    public Perk getById(long id) {
        return perkDao.getById(id)
                .orElseThrow(() -> new RuntimeException("Perk with this id doesn't exist. Id: " + id));
    }

    @Override
    public Perk save(String name, double price) {
        EntityValidityCheck.perkPriceCheck(price);
        return perkDao.save(new Perk(IdGenerator.generatePerkId(), name, price));
    }

    public void save(List<Perk> perks) {
        for (Perk perk : perks) {
            perkDao.save(perk);
            IdGenerator.generatePerkId();
        }
    }

    @Override
    public Perk update(Perk perk) {
        return perkDao.update(perk)
                .orElseThrow(() -> new RuntimeException("Perk with this id doesn't exist. Id: " + perk.getId()));
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
            case NAME -> sortByName();
            case PRICE -> sortByPrice();
        };
    }

    private List<Perk> sortByName() {
        return getAll().stream()
                .sorted(Comparator.comparing(Perk::getName))
                .toList();
    }

    private List<Perk> sortByPrice() {
        return getAll().stream()
                .sorted(Comparator.comparing(Perk::getPrice))
                .toList();
    }
}
