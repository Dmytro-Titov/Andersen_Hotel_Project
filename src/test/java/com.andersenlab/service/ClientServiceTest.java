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

    //private static ClientService clientService;
    protected static ClientDao clientDao = new ClientDaoImpl();
    protected static ClientService clientService = new ClientServiceImpl(clientDao, PerkServiceTest.perkDao,
            ApartmentServiceTest.apartmentDao);
    protected static ApartmentService apartmentService = ApartmentServiceTest.apartmentService;
    protected static PerkService perkService = PerkServiceTest.perkService;


    @BeforeAll
    static void setup() {
//        ClientDao clientDao = new ClientDaoImpl();
//        ApartmentDao apartmentDao = new ApartmentDaoImpl();
//        PerkDao perkDao = new PerkDaoImpl();
//        clientService = new ClientServiceImpl(clientDao, perkDao, apartmentDao);
//        ApartmentService apartmentService = new ApartmentServiceImpl(apartmentDao);

        //PerkService perkService = new PerkServiceImpl(perkDao);
        clientService.save("Oleg", 4);
        clientService.save("Alex", 2);
        clientService.save("Petr", 3);
        clientService.save("Lola", 2);
        clientService.save("Ivan", 1);
        clientService.save("Evgen", 4);
        clientService.save("Olga", 2);
        clientService.save("Valera", 5);
        clientService.save("Vlad", 2);

        apartmentService.save(10, 1, 500.0);
        apartmentService.save(11, 2, 350.0);
        apartmentService.save(12, 4, 500.0);
        apartmentService.save(13, 1, 200.0);
        apartmentService.save(14, 1, 200.0);
        apartmentService.save(15, 4, 500.0);
        apartmentService.save(16, 2, 350.0);



        apartmentService.save(1, 4, 500.0);

        apartmentService.save(2, 2, 350.0);
        apartmentService.save(3, 4, 500.0);
        apartmentService.save(4, 2, 200.0);

        apartmentService.save(5, 1, 200.0);
        apartmentService.save(6, 4, 500.0);
        apartmentService.save(7, 2, 350.0);
        apartmentService.save(8, 2, 200.0);
        apartmentService.save(9, 5, 550.0);
        apartmentService.save(10, 1, 200.0);
        perkService.save("ironing", 100);
        perkService.save("laundry", 100);
    }


    @Test
    void sortedByNameTest() {
        assertEquals("Alex", clientService.sortByName().stream().findFirst().get().getName());
    }


    @Test
    void checkInTest() {
        assertTrue(clientService.checkInApartment(5, 12, 3));
    }

    @Test
    void checkInAnyFreeApartmentsTest() {
        assertTrue(clientService.checkInAnyFreeApartment(8, 1));
    }

    @Test
    void checkOutTest() {
        clientService.checkInApartment(2, 10, 3);
        assertTrue(clientService.checkOutApartment(2));
    }


    @Test
    void sortedByCheckOutDateTest() {
        System.out.println(apartmentService.getById(11).getStatus());
        System.out.println(clientService.getById(9).getStatus());
        System.out.println(apartmentService.getById(12).getStatus());
        System.out.println(clientService.getById(3).getStatus());
        System.out.println(apartmentService.getById(9).getStatus());
        System.out.println(clientService.getById(4).getStatus());
        clientService.checkInApartment(9, 11, 5);
        clientService.checkInApartment(3, 10, 8);
        clientService.checkInApartment(4, 9, 1);

        clientService.sortByCheckOutDate().forEach(client -> System.out.println(client.getName()+client.getCheckOutDate()));
        assertEquals("Lola", clientService.sortByCheckOutDate().stream().findFirst().get().getName());
    }


//    @Test
//    void sortedByStatusTest() {
//        clientService.checkInApartment(1, 1, 5);
//        clientService.checkInApartment(7, 8, 10);
//        clientService.checkOutApartment(7);
//        assertEquals("Oleg", clientService.sortByCheckOutDate().stream().findFirst().get().getName());
//    }


    @Test
    void getStayCoastTest() {
        clientService.checkInApartment(6, 6, 5);
        clientService.addPerk(6, 1);
        clientService.addPerk(6, 2);
        assertEquals(2700, clientService.getStayCost(6));
    }


    @Test
    void getAllPerksTest() {
        clientService.checkInApartment(7, 7, 7);
        clientService.addPerk(1, 1);
        clientService.addPerk(1, 2);
        assertEquals(2, clientService.getAllPerks(1).size());
    }
}