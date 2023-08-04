package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.impl.ApartmentDaoImpl;
import com.andersenlab.dao.impl.ClientDaoImpl;
import com.andersenlab.dao.impl.PerkDaoImpl;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import com.andersenlab.service.impl.ClientServiceImpl;
import com.andersenlab.service.impl.PerkServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientServiceTest {

    private static ClientService clientService;

//    @BeforeAll
//    static void setup() {
//        ClientDao clientDao = new ClientDaoImpl();
//        ApartmentDao apartmentDao = new ApartmentDaoImpl();
//        PerkDao perkDao = new PerkDaoImpl();
//        clientService = new ClientServiceImpl(clientDao, perkDao, apartmentDao);
//        ApartmentService apartmentService = new ApartmentServiceImpl(apartmentDao);
//        PerkService perkService = new PerkServiceImpl(perkDao);
//        clientService.save("Oleg");
//        clientService.save("Alex");
//        clientService.save("Petr");
//        clientService.save("Lola");
//        clientService.save("Ivan");
//        clientService.save("Evgen");
//        apartmentService.save(1, 4, 500.0);
//        apartmentService.save(2, 2, 350.0);
//        apartmentService.save(3, 4, 500.0);
//        apartmentService.save(4, 1, 200.0);
//        apartmentService.save(5, 1, 200.0);
//        apartmentService.save(6, 4, 500.0);
//        apartmentService.save(7, 2, 350.0);
//        perkService.save("ironing", 100);
//        perkService.save("laundry", 100);
//    }
//
//
//    @Test
//    void sortedByNameTest() {
//        assertEquals("Alex", clientService.sortByName().stream().findFirst().get().getName());
//    }
//
//
//    @Test
//    void checkInTest() {
//        assertTrue(clientService.checkInApartment(5, 5, 3));
//    }
//
//
//    @Test
//    void checkOutTest() {
//        clientService.checkInApartment(2, 3, 3);
//        assertTrue(clientService.checkOutApartment(2));
//    }
//
//
//    @Test
//    void sortedByCheckOutDateTest() {
//        clientService.checkInApartment(2, 4, 5);
//        clientService.checkInApartment(3, 3, 8);
//        clientService.checkInApartment(4, 2, 1);
//        assertEquals("Lola", clientService.sortByCheckOutDate().stream().findFirst().get().getName());
//    }
//
//
//    @Test
//    void sortedByStatusTest() {
//        clientService.checkInApartment(2, 4, 5);
//        clientService.checkInApartment(4, 2, 1);
//        clientService.checkOutApartment(2);
//        assertEquals("Alex", clientService.sortByCheckOutDate().stream().findFirst().get().getName());
//    }
//
//
//    @Test
//    void getStayCoastTest() {
//        clientService.checkInApartment(6, 6, 5);
//        clientService.addPerk(1, 1);
//        clientService.addPerk(1, 2);
//        assertEquals(2700, clientService.getStayCost(6));
//    }
//
//
//    @Test
//    void getAllPerksTest() {
//        clientService.checkInApartment(1, 7, 7);
//        clientService.addPerk(1, 1);
//        clientService.addPerk(1, 2);
//        assertEquals(2, clientService.getAllPerks(1).size());
//    }
}
