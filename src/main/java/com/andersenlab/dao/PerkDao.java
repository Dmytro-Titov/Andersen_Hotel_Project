package com.andersenlab.dao;

import com.andersenlab.entity.Perk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PerkDao {
    List<Perk> perks;

    public PerkDao() {
        this.perks = new ArrayList<>();
    }
    public Perk add(Perk perk) {
        perks.add(perk);
        return perks.get(perks.size() - 1);
    }
    public void setPrice(Perk perkToBeChanged, double newPrice) {
        for (Perk perk : perks) {
            if (perk.equals(perkToBeChanged)) {
                perk.setPrice(newPrice);
            }
        }
    }
    public List<Perk> sortByName() {
        List<Perk> sortedByName = new ArrayList<>(perks);
        sortedByName.sort(Comparator.comparing(Perk::getName));
        return sortedByName;
    }
    public List<Perk> sortByPrice() {
        List<Perk> sortedByPrice = new ArrayList<>(perks);
        sortedByPrice.sort(Comparator.comparing(Perk::getPrice));
        return sortedByPrice;
    }
}
