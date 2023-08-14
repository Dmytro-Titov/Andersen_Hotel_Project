package com.andersenlab.service;

import com.andersenlab.config.Config;
import com.andersenlab.dao.onDiskImpl.OnDiskApartmentDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskClientDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskPerkDaoImpl;
import com.andersenlab.entity.*;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ConfigHandler;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClientServiceTest {

    private ClientService clientService;
    private HotelFactory hotelFactory;

    @BeforeEach
    private void setup() {
        IdGenerator.cancelGenerateId();
        Config config = new Config();
        config.setConfigData(ConfigHandler.createConfig("src/main/resources/config/config-dev.yaml"));
        config.getConfigData().getDatabase().setPath("src/main/resources/json/testHotel.json");
        hotelFactory = new HotelFactory(config);
        clientService = hotelFactory.getClientService();
        ApartmentService apartmentService = hotelFactory.getApartmentService();
        PerkService perkService = hotelFactory.getPerkService();
        clientService.save("Oleg", 2);
        clientService.save("Alex", 2);
        clientService.save("Petr", 3);
        clientService.save("Lola", 2);
        apartmentService.save(5, 200.0);
        apartmentService.save(5, 550.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(6, 200.0);
        perkService.save("ironing", 150);
        perkService.save("laundry", 100);
    }

    @AfterEach
    private void teardown() {
        OnDiskClientDaoImpl onDiskClientDao = new OnDiskClientDaoImpl(hotelFactory);
        for (Client client : clientService.getAll()) {
            onDiskClientDao.remove(client.getId());
        }
        OnDiskApartmentDaoImpl onDiskApartmentDao = new OnDiskApartmentDaoImpl(hotelFactory);
        for (Apartment apartment : hotelFactory.getApartmentService().getAll()) {
            onDiskApartmentDao.remove(apartment.getId());
        }
        OnDiskPerkDaoImpl onDiskPerkDao = new OnDiskPerkDaoImpl(hotelFactory);
        for (Perk perk : hotelFactory.getPerkService().getAll()) {
            onDiskPerkDao.remove(perk.getId());
        }
    }


    @Test
    void given4Clients_whenSortByID_thenGetCorrectListTest() {
        //given
        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(new Client(1, "Oleg", 2));
        expectedClients.add(new Client(2, "Alex", 2));
        expectedClients.add(new Client(3, "Petr", 3));
        expectedClients.add(new Client(4, "Lola", 2));
        //when
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.ID);
        //then
        assertEquals(expectedClients, actualClients);
    }


    @Test
    void given4Clients_whenSortByName_thenGetCorrectListTest() {
        //given
        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(new Client(2, "Alex", 2));
        expectedClients.add(new Client(4, "Lola", 2));
        expectedClients.add(new Client(1, "Oleg", 2));
        expectedClients.add(new Client(3, "Petr", 3));
        //when
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.NAME);
        //then
        assertEquals(expectedClients, actualClients);
    }


    @Test
    void given4Clients_whenSortByCheckOutDate_thenGetCorrectListTest() {
        //given
        List<Client> expectedClients = new ArrayList<>();
        Client client1 = new Client(1, "Oleg", 2);
        Client client2 = new Client(2, "Alex", 2);
        Client client3 = new Client(3, "Petr", 3);
        Client client4 = new Client(4, "Lola", 2);
        client1.setCheckOutDate(LocalDateTime.of(2023, 8, 19, 12, 0));
        client2.setCheckOutDate(LocalDateTime.of(2023, 8, 24, 12, 0));
        client3.setCheckOutDate(LocalDateTime.of(2023, 8, 15, 12, 0));
        client4.setCheckOutDate(LocalDateTime.of(2023, 8, 21, 12, 0));
        expectedClients.add(client3);
        expectedClients.add(client1);
        expectedClients.add(client4);
        expectedClients.add(client2);
        //when
        clientService.checkInApartment(1, 5, 1);
        clientService.checkInApartment(2, 10, 2);
        clientService.checkInApartment(3, 1, 3);
        clientService.checkInApartment(4, 7, 4);
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
        //then
        for (int i = 0; i < actualClients.size(); i++) {

            assertSame(actualClients.get(i).getCheckOutDate().getDayOfMonth(),
                    expectedClients.get(i).getCheckOutDate().getDayOfMonth());
        }
    }


    @Test
    void given4Clients_whenSortByStatus_thenGetCorrectListTest() {
        //given
        List<Client> expectedClients = new ArrayList<>();
        Client client1 = new Client(1, "Oleg", 2);
        Client client2 = new Client(2, "Alex", 2);
        Client client3 = new Client(3, "Petr", 3);
        Client client4 = new Client(4, "Lola", 2);
        client1.setStatus(ClientStatus.CHECKED_IN);
        client3.setStatus(ClientStatus.CHECKED_OUT);
        client4.setStatus(ClientStatus.CHECKED_IN);
        expectedClients.add(client1);
        expectedClients.add(client4);
        expectedClients.add(client3);
        //when
        clientService.checkInApartment(1, 5, 1);
        clientService.checkInApartment(3, 10, 2);
        clientService.checkOutApartment(3);
        clientService.checkInApartment(4, 7, 2);
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
        //then
        for (int i = 0; i < actualClients.size(); i++) {
            assertSame(actualClients.get(i).getStatus(), expectedClients.get(i).getStatus());
        }
    }


    @Test
    void givenCost_whenCheckInApartmentsAndAddPerks_thenGetCorrectStayCost() {
        //given
        int expectedClientPerkSize = 2;
        double expectedClientStayCoast = 650.0;
        //when
        clientService.checkInApartment(4, 2, 4);
        System.out.println(clientService.getById(4).getStayCost());
        System.out.println(clientService.getById(4).getApartment());
        clientService.addPerk(4, 1);
        clientService.addPerk(4, 2);
        //then
        assertEquals(expectedClientPerkSize, clientService.getAllPerks(4).size());
        assertEquals(expectedClientStayCoast, clientService.getStayCost(4));
    }
}