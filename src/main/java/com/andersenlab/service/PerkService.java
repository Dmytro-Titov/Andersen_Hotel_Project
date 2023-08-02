package com.andersenlab.service;

import com.andersenlab.entity.Perk;

import java.util.*;

public interface PerkService {
    Perk getById(long id);

    void save(String name, double price);

    void changePrice(long id, double price);

    List<Perk> sortByName();

    List<Perk> sortByPrice();


}
