package com.andersenlab.service;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.impl.PerkDaoImpl;
import com.andersenlab.service.impl.PerkServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkServiceTest {

    //private static PerkService perkService;
    protected static PerkDao perkDao = new PerkDaoImpl();
    protected static PerkService perkService = new PerkServiceImpl(perkDao);

    @BeforeAll
    static void setup() {
        //PerkDao perkDao = new PerkDaoImpl();
        //perkService = new PerkServiceImpl(perkDao);
        perkService.save("ironing", 100);
        perkService.save("laundry", 100);
        perkService.save("massage", 300);
    }


    @Test
    void sortedByPriceTest() {
        perkService.changePrice(2, 50);
        assertEquals("laundry", perkService.sortByPrice().stream().findFirst().get().getName());
    }


    @Test
    void sortByNameTest() {
        assertEquals("ironing", perkService.sortByName().stream().findFirst().get().getName());
    }
}
