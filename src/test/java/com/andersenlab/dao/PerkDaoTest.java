package com.andersenlab.dao;

import com.andersenlab.dao.impl.PerkDaoImpl;
import com.andersenlab.entity.Perk;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class PerkDaoTest {
    private PerkDao perkDao;
    private Perk perk;

    @BeforeEach
    private void setup() {
        perk = new Perk();
        perk.setId(IdGenerator.generatePerkId());
        perk.setName("Name");
        perk.setPrice(10);

        Perk perk1 = new Perk();
        perk1.setId(IdGenerator.generatePerkId());
        perk1.setName("Name2");
        perk1.setPrice(20);

        Perk perk2 = new Perk();
        perk2.setId(IdGenerator.generatePerkId());
        perk2.setName("Name3");
        perk2.setPrice(30);

        perkDao = new PerkDaoImpl();
        perkDao.save(perk);
        perkDao.save(perk1);
        perkDao.save(perk2);
    }

    @Test
    void getByIdTest() {
        Assertions.assertEquals(perk, perkDao.getById(perk.getId()).orElse(null));
        Assertions.assertTrue(perkDao.getById(Long.MAX_VALUE).isEmpty());
    }

    @Test
    void getAllTest() {
        Assertions.assertEquals(3, (long) perkDao.getAll().size());
    }

    @Test
    void saveTest() {
        Assertions.assertNotNull(perkDao.save(perk));
        Assertions.assertEquals(perk.getId(), Objects.requireNonNull(perkDao.getById(perk.getId()).orElse(null)).getId());
    }

    @Test
    void updateTest() {
        perk.setPrice(100);

        Assertions.assertAll("perkUpdate",
                () -> Assertions.assertNotNull(perkDao.update(perk)),
                () -> Assertions.assertEquals(perk,perkDao.update(perk).orElse(null))
        );
    }

    @Test
    void removeTest() {
        Assertions.assertTrue(perkDao.remove(perk.getId()));
        Assertions.assertTrue(perkDao.getById(perk.getId()).isEmpty());
    }
}
