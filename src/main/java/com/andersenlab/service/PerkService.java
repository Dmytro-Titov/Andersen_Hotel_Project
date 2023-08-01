package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.entity.Perk;
import com.andersenlab.util.IdGenerator;

public class PerkService {

    private final ClientDao clientDao = ClientDao.getInstance();
    private final ApartmentDao apartmentDao = ApartmentDao.getInstance();

    private final PerkDao perkDao = PerkDao.getInstance();

    public Long createAndAddNewPerk(String name, double price) {
        return perkDao.addPerk(new Perk(IdGenerator.generatePerkId(), name, price));
    }

    public Perk getPerk(Long id) {
        return perkDao.getPerkById(id);
    }
}
