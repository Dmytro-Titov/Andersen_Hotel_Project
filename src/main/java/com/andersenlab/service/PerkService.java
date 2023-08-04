package com.andersenlab.service;

import com.andersenlab.entity.Perk;

import java.util.*;

public interface PerkService {
    Perk getById(long id);

    Perk save(String name, double price);

    Perk changePrice(long id, double price);

    List<Perk> getAll();

    List<Perk> getSorted(PerkSortType type);

    List<Perk> sortByName();

    List<Perk> sortByPrice();

    enum PerkSortType {
        ID, NAME, PRICE
    }
}
