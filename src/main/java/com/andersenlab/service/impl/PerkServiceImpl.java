package com.andersenlab.service.impl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.PerkService;
import com.andersenlab.util.IdGenerator;

import java.util.ArrayList;
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
        Perk perk = new Perk(IdGenerator.generatePerkId(), name, price);
        return perkDao.save(perk);
    }

    @Override
    public Perk update(Perk perk) {
        return perkDao.update(perk)
                .orElseThrow(() -> new RuntimeException("Perk with this id doesn't exist. Id: " + perk.getId()));
    }

    @Override
    public Perk changePrice(long id, double price) {
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
        List<Perk> sortedByName = new ArrayList<>(getAll());
        sortedByName.sort(Comparator.comparing(Perk::getName));
        return sortedByName;
    }

    private List<Perk> sortByPrice() {
        List<Perk> sortedByPrice = new ArrayList<>(getAll());
        sortedByPrice.sort(Comparator.comparing(Perk::getPrice));
        return sortedByPrice;
    }
}
