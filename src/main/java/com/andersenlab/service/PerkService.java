package com.andersenlab.service;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;

import java.util.*;

public class PerkService {
    private final PerkDao perkDao;

    public PerkService(PerkDao perkDao) {
        this.perkDao = perkDao;
    }
    public void setPrice(String perkName, double newPrice) {
        Set<Perk> perks = perkDao.getAll();
        for (Perk perk : perks) {
            if (perk.getName().equals(perkName)) {
                perk.setPrice(newPrice);
            }
        }
    }
    public List<Perk> sortByName() {
        if (perkDao.getAll() == null || perkDao.getAll().isEmpty()) {
            return null;
        }
        List<Perk> sortedByName = new ArrayList<>(perkDao.getAll());
        sortedByName.sort(Comparator.comparing(Perk::getName));
        return sortedByName;
    }
    public List<Perk> sortByPrice() {
        if (perkDao.getAll() == null || perkDao.getAll().isEmpty()) {
            return null;
        }
        List<Perk> sortedByPrice = new ArrayList<>(perkDao.getAll());
        sortedByPrice.sort(Comparator.comparing(Perk::getPrice));
        return sortedByPrice;
    }
}
