package com.andersenlab.dao.impl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerkDaoImpl implements PerkDao {

    private final List<Perk> perks;

    public PerkDaoImpl() {
        this.perks = new ArrayList<>();
    }

    @Override
    public Optional<Perk> getById(long id) {
        return perks.stream()
                .filter(perk -> perk.getId() == id)
                .findFirst();
    }

    @Override
    public List<Perk> getAll() {
        return perks;
    }

    @Override
    public Perk save(Perk perk) {
        perks.add(perk);
        return perk;
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        Optional<Perk> existingPerk = getById(perk.getId());
        if (existingPerk.isPresent()) {
            if (perk.getName() != null) {
                existingPerk.get().setName(perk.getName());
            }
            existingPerk.get().setPrice(perk.getPrice());
            return existingPerk;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean remove(long id) {
        return perks.removeIf(perk -> perk.getId() == id);
    }
}
