package com.andersenlab.dao;

import com.andersenlab.entity.Perk;

import java.util.*;

public interface PerkDao {
    Perk getById(long id);

    List<Perk> getAll();

    void save(Perk perk);

    void update(Perk perk);

    void remove(long id);
}
