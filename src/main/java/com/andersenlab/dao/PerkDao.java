package com.andersenlab.dao;

import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;

import java.util.*;
import java.util.stream.Collectors;

public class PerkDao {
    private static PerkDao instance;
    private Set<Perk> perkSet = new HashSet<>();

    private PerkDao() {
    }

    public static PerkDao getInstance() {
        if (instance == null) {
            instance = new PerkDao();
        }
        return instance;
    }

    public Long addPerk(Perk newPerk) {
        perkSet.add(newPerk);
        return newPerk.getId();
    }

    public Optional<Perk> getPerkById(Long id) {
        return perkSet.stream()
                .filter(perk -> perk.getId().equals(id))
                .findFirst();
    }

    public Collection<Perk> getAllPerks() {
        return perkSet.stream().toList();
    }

    public boolean removePerk(Perk perk) {
        return perkSet.remove(perk);
    }

    public boolean isPerkPresent(Perk perk) {
        return perkSet.contains(perk);
    }

    public List<Perk> sortByName() {
        return perkSet.stream()
                .sorted(Comparator.comparing(Perk::getName))
                .collect(Collectors.toList());
    }

    public List<Perk> sortByPrice() {
        return perkSet.stream()
                .sorted(Comparator.comparing(Perk::getPrice))
                .collect(Collectors.toList());
    }


}
