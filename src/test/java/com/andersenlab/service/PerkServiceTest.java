package com.andersenlab.service;

import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PerkServiceTest {

    private PerkService perkService;


    @BeforeEach
    void setup() {
        IdGenerator.cancelGenerateId();
        HotelFactory hotelFactory = new HotelFactory();
        perkService = hotelFactory.getPerkService();
        perkService.save("massage", 300);
        perkService.save("ironing", 150);
        perkService.save("laundry", 100);
    }


    @Test
    void given3Perks_whenSortByID_thenGetCorrectListTest() {
        //given
        Perk perk1 = new Perk(1, "massage", 300);
        Perk perk2 = new Perk(2, "ironing", 150);
        Perk perk3 = new Perk(3, "laundry", 100);
        List<Perk> expectedPerks = new ArrayList<>();
        expectedPerks.add(perk1);
        expectedPerks.add(perk2);
        expectedPerks.add(perk3);
        //when
        List<Perk> actualPerks = perkService.getSorted(PerkService.PerkSortType.ID);
        //then
        assertEquals(expectedPerks, actualPerks);
    }


    @Test
    void given3Perks_whenSortByName_thenGetCorrectListTest() {
        //given
        Perk perk1 = new Perk(1, "massage", 300);
        Perk perk2 = new Perk(2, "ironing", 150);
        Perk perk3 = new Perk(3, "laundry", 100);
        List<Perk> expectedPerks = new ArrayList<>();
        expectedPerks.add(perk2);
        expectedPerks.add(perk3);
        expectedPerks.add(perk1);
        //when
        List<Perk> actualPerks = perkService.getSorted(PerkService.PerkSortType.NAME);
        //then
        assertEquals(expectedPerks, actualPerks);
    }


    @Test
    void given3Perks_whenSortByPrice_thenGetCorrectListTest() {
        //given
        Perk perk1 = new Perk(1, "massage", 50);
        Perk perk2 = new Perk(2, "ironing", 150);
        Perk perk3 = new Perk(3, "laundry", 100);
        List<Perk> expectedPerks = new ArrayList<>();
        expectedPerks.add(perk1);
        expectedPerks.add(perk3);
        expectedPerks.add(perk2);
        //when
        perkService.changePrice(1, 50.0);
        List<Perk> actualPerks = perkService.getSorted(PerkService.PerkSortType.PRICE);
        //then
        assertEquals(expectedPerks, actualPerks);
    }
}
