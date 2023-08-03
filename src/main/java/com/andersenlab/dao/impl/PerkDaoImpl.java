package com.andersenlab.dao.impl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;

import java.util.ArrayList;
import java.util.List;

public class PerkDaoImpl implements PerkDao {

    private List<Perk> perks;

    public PerkDaoImpl() {
        this.perks = new ArrayList<>();
    }

    @Override
    public Perk getById(long id) {
        for (Perk perk : perks) {
            if (perk.getId() == id)
                return perk;
        }
        return null;
    }

    @Override
    public List<Perk> getAll() {
        return perks;
    }

    @Override
    public void save(Perk perk) {
        perks.add(perk);
    }

    @Override
    public void update(Perk perk) {
        Perk existingPerk = getById(perk.getId());
        if (perk.getName() != null) {
            existingPerk.setName(perk.getName());
        }
        existingPerk.setPrice(perk.getPrice());
    }

    @Override
    public void remove(long id) {
        perks.removeIf(perk -> perk.getId() == id);
    }
}
