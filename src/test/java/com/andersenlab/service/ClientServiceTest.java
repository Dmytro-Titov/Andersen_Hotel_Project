package com.andersenlab.service;

import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.impl.ClientDaoImpl;
import com.andersenlab.entity.ClientStatus;
import com.andersenlab.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceTest {

    protected static ClientDao clientDao = new ClientDaoImpl();
    protected static ClientService clientService = new ClientServiceImpl(clientDao, PerkServiceTest.perkDao,
            ApartmentServiceTest.apartmentDao);
    protected static ApartmentService apartmentService = ApartmentServiceTest.apartmentService;
    protected static PerkService perkService = PerkServiceTest.perkService;


    @BeforeAll
    static void setup() {
        clientService.save("Oleg", 2);
        clientService.save("Alex", 2);
        clientService.save("Petr", 3);
        clientService.save("Lola", 2);
        clientService.save("Ivan", 1);
        clientService.save("Evgen", 4);
        clientService.save("Olga", 2);
        clientService.save("Valera", 5);
        clientService.save("Vlad", 2);

        apartmentService.save(4, 500.0);
        apartmentService.save(2, 350.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(2, 200.0);
        apartmentService.save(1, 200.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(2, 350.0);
        apartmentService.save(2, 200.0);
        apartmentService.save(5, 550.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(2, 200.0);
        perkService.save("ironing", 150);
        perkService.save("laundry", 100);
        perkService.save("massage", 300);
    }


    @Test
    void getSortedTest() {
        clientService.checkInApartment(1, 5, 8);
        clientService.checkInApartment(2, 8, 9);
        clientService.checkInApartment(3, 1, 10);
        clientService.checkOutApartment(3);
        clientService.checkOutApartment(2);
        assertEquals("Oleg", clientService.getSorted(ClientService.ClientSortType.ID)
                .stream().findFirst().get().getName());
        assertEquals("Alex", clientService.getSorted(ClientService.ClientSortType.NAME)
                .stream().findFirst().get().getName());
        assertEquals("Petr", clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE)
                .stream().findFirst().get().getName());
        assertEquals("Oleg", clientService.getSorted(ClientService.ClientSortType.STATUS)
                .stream().filter(client -> client.getStatus().equals(ClientStatus.CHECKED_IN)).findFirst().get().getName());
    }

    @Test
    void getStayCostTest() {
        clientService.checkInApartment(4, 2, 11);
        clientService.addPerk(4, 1);
        clientService.addPerk(4, 2);
        assertEquals(2, clientService.getAllPerks(4).size());
        assertEquals(650, clientService.getStayCost(4));
    }
}