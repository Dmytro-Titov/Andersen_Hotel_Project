package com.andersenlab.dao;

import com.andersenlab.entity.Perk;

import java.util.*;

public class PerkDao {
    Set<Perk> perks;

    public PerkDao() {
        this.perks = new HashSet<>();
    }

    public void save(Perk perk) {
        perks.add(perk);
    }

    public Set<Perk> getAll() {
        return perks;
    }
}
