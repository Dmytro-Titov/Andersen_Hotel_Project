package com.andersenlab.service;

import com.andersenlab.entity.ClientStatus;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientServiceTest {

    private ClientService clientService;

    @BeforeEach
    private void setup() {
        IdGenerator.cancelGenerateId();
        HotelFactory hotelFactory = new HotelFactory();
        clientService = hotelFactory.getClientService();
        ApartmentService apartmentService = hotelFactory.getApartmentService();
        PerkService perkService = hotelFactory.getPerkService();
        clientService.save("Oleg", 2);
        clientService.save("Alex", 2);
        clientService.save("Petr", 3);
        clientService.save("Lola", 2);
        apartmentService.save(2, 200.0);
        apartmentService.save(5, 550.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(2, 200.0);
        perkService.save("ironing", 150);
        perkService.save("laundry", 100);
    }

    @Test
    void geByIdTest() {
        assertEquals("Oleg", clientService.getById(1).getName());
    }


    @Test
    void getSortedTest() {
        clientService.checkInApartment(1, 5, 1);
        clientService.checkInApartment(2, 8, 2);
        clientService.checkInApartment(3, 1, 3);
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
        clientService.checkInApartment(4, 2, 4);
        clientService.addPerk(4, 1);
        clientService.addPerk(4, 2);
        assertEquals(2, clientService.getAllPerks(4).size());
        assertEquals(650, clientService.getStayCost(4));
    }
}