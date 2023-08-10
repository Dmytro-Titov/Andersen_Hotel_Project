package com.andersenlab.service;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.ClientStatus;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        Client client1 = new Client(1, "Oleg", 2);
        Client client2 = new Client(2, "Alex", 2);
        Client client3 = new Client(3, "Petr", 3);
        Client client4 = new Client(4, "Lola", 2);
        Apartment apartment1 = new Apartment(1, 200.0);
        Apartment apartment2 = new Apartment(2, 550.0);
        Apartment apartment3 = new Apartment(3, 500.0);
        Apartment apartment4 = new Apartment(4, 200.0);
        apartment1.setCapacity(2);
        apartment2.setCapacity(5);
        apartment3.setCapacity(4);
        apartment4.setCapacity(2);
        apartment1.setStatus(ApartmentStatus.UNAVAILABLE);
        apartment2.setStatus(ApartmentStatus.UNAVAILABLE);
        apartment3.setStatus(ApartmentStatus.UNAVAILABLE);
        apartment4.setStatus(ApartmentStatus.UNAVAILABLE);
        client1.setApartment(apartment1);
        client2.setApartment(apartment2);
        client3.setApartment(apartment3);
        client4.setApartment(apartment4);
        client1.setStatus(ClientStatus.CHECKED_IN);
        client2.setStatus(ClientStatus.CHECKED_IN);
        client3.setStatus(ClientStatus.CHECKED_IN);
        client4.setStatus(ClientStatus.CHECKED_IN);
        client1.setStayCost(1000.0);
        client2.setStayCost(4400.0);
        client3.setStayCost(500.0);
        client4.setStayCost(400.0);
        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(client3);
        expectedClients.add(client4);
        expectedClients.add(client1);
        expectedClients.add(client2);
        //when
        clientService.checkInApartment(1, 5, 1);
        clientService.checkInApartment(2, 8, 2);
        clientService.checkInApartment(3, 1, 3);
        clientService.checkInApartment(4, 2, 4);
        client1.setCheckInDate(clientService.getById(1).getCheckInDate());
        client2.setCheckInDate(clientService.getById(2).getCheckInDate());
        client3.setCheckInDate(clientService.getById(3).getCheckInDate());
        client4.setCheckInDate(clientService.getById(4).getCheckInDate());
        client1.setCheckOutDate(clientService.getById(1).getCheckOutDate());
        client2.setCheckOutDate(clientService.getById(2).getCheckOutDate());
        client3.setCheckOutDate(clientService.getById(3).getCheckOutDate());
        client4.setCheckOutDate(clientService.getById(4).getCheckOutDate());
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.CHECK_OUT_DATE);
        //then
        assertEquals(expectedClients, actualClients);
    }


    @Test
    void given4Clients_whenSortByStatus_thenGetCorrectListTest() {
        //given
        Client client1 = new Client(1, "Oleg", 2);
        Client client2 = new Client(2, "Alex", 2);
        Client client3 = new Client(3, "Petr", 3);
        Client client4 = new Client(4, "Lola", 2);
        Apartment apartment1 = new Apartment(1, 200.0);
        Apartment apartment4 = new Apartment(4, 200.0);
        apartment1.setCapacity(2);
        apartment4.setCapacity(2);
        apartment1.setStatus(ApartmentStatus.UNAVAILABLE);
        apartment4.setStatus(ApartmentStatus.UNAVAILABLE);
        client1.setApartment(apartment1);
        client4.setApartment(apartment4);
        client1.setStatus(ClientStatus.CHECKED_IN);
        client2.setStatus(ClientStatus.CHECKED_OUT);
        client3.setStatus(ClientStatus.CHECKED_OUT);
        client4.setStatus(ClientStatus.CHECKED_IN);
        client1.setStayCost(1000.0);
        client4.setStayCost(400.0);
        List<Client> expectedClients = new ArrayList<>();
        expectedClients.add(client1);
        expectedClients.add(client4);
        expectedClients.add(client2);
        expectedClients.add(client3);
        //when
        clientService.checkInApartment(1, 5, 1);
        clientService.checkInApartment(2, 8, 2);
        clientService.checkInApartment(3, 1, 3);
        clientService.checkInApartment(4, 2, 4);
        clientService.checkOutApartment(3);
        clientService.checkOutApartment(2);
        client1.setCheckInDate(clientService.getById(1).getCheckInDate());
        client2.setCheckInDate(clientService.getById(2).getCheckInDate());
        client3.setCheckInDate(clientService.getById(3).getCheckInDate());
        client4.setCheckInDate(clientService.getById(4).getCheckInDate());
        client1.setCheckOutDate(clientService.getById(1).getCheckOutDate());
        client2.setCheckOutDate(clientService.getById(2).getCheckOutDate());
        client3.setCheckOutDate(clientService.getById(3).getCheckOutDate());
        client4.setCheckOutDate(clientService.getById(4).getCheckOutDate());
        List<Client> actualClients = clientService.getSorted(ClientService.ClientSortType.STATUS);
        //then
        assertEquals(expectedClients, actualClients);
    }


    @Test
    void givenCoast_whenCheckInApartmentsAndAddPerks_thenGetCorrectStayCoast() {
        //given
        int expectedClientPerkSize = 2;
        double expectedClientStayCoast = 650.0;
        //when
        clientService.checkInApartment(4, 2, 4);
        clientService.addPerk(4, 1);
        clientService.addPerk(4, 2);
        //then
        assertEquals(expectedClientPerkSize, clientService.getAllPerks(4).size());
        assertEquals(expectedClientStayCoast, clientService.getStayCost(4));
    }
}