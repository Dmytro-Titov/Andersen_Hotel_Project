package com.andersenlab.dao;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Perk;

import java.util.*;

public class PerkDao {
    List<Perk> perks;

    public PerkDao() {
        this.perks = new ArrayList<>();
    }

    public void save(Perk perk) {
        perks.add(perk);
    }

    public List<Perk> getAll() {
        return perks;
    }
    public Perk get(long id) {
        for (Perk perk: perks) {
            if (perk.getId() == id)
                return perk;
        }
        return null;
    }
}
