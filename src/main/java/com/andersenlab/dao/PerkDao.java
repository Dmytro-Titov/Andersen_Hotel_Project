package com.andersenlab.dao;

import com.andersenlab.entity.Perk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PerkDao {
    private List<Perk> perks;

    public PerkDao() {
        this.perks = new ArrayList<>();
        this.perks.add(new Perk(1, "Flower arrangement", 750.0));
        this.perks.add(new Perk(2, "Laundry", 200.0));
        this.perks.add(new Perk(3, "Ironing", 100.0));
        this.perks.add(new Perk(4, "Massage", 600.0));
        this.perks.add(new Perk(5, "Food delivery", 100.0));
    }
    public Perk add(Perk perk) {
        perks.add(perk);
        return perks.get(perks.size() - 1);
    }
    public Perk changePerkPrice(Perk perk, double newPrice) {
        Perk result = null;
        for (Perk option : perks) {
            if (option.equals(perk)) {
                option.setPrice(newPrice);
                result = option;
            }
        } return result;
    }
    public List<Perk> listByName() {
        List<Perk> sortedByName = new ArrayList<>(perks);
        sortedByName.sort(Comparator.comparing(Perk::getName));
        return sortedByName;
    }
    public List<Perk> listByPrice() {
        List<Perk> sortedByPrice = new ArrayList<>(perks);
        sortedByPrice.sort(Comparator.comparing(Perk::getPrice));
        return sortedByPrice;
    }

}
