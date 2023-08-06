package com.andersenlab.service;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.impl.PerkDaoImpl;
import com.andersenlab.service.impl.PerkServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkServiceTest {

    protected static PerkDao perkDao = new PerkDaoImpl();
    protected static PerkService perkService = new PerkServiceImpl(perkDao);


    @BeforeAll
    static void setup() {
        perkService.save("ironing", 150);
        perkService.save("laundry", 100);
        perkService.save("massage", 300);
    }


    @Test
    void geByIdTest() {
        assertEquals("laundry", perkService.getById(2).getName());
    }


    @Test
    void getSortedTest() {
        perkService.changePrice(3, 50.0);
        assertEquals("ironing", perkService.getSorted(PerkService.PerkSortType.ID)
                .stream().findFirst().get().getName());
        assertEquals("ironing", perkService.getSorted(PerkService.PerkSortType.NAME)
                .stream().findFirst().get().getName());
        assertEquals("massage", perkService.getSorted(PerkService.PerkSortType.PRICE)
                .stream().findFirst().get().getName());
    }
}
