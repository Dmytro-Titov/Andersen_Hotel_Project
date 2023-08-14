package com.andersenlab.service;

import com.andersenlab.config.Config;
import com.andersenlab.dao.onDiskImpl.OnDiskApartmentDaoImpl;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.ConfigHandler;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApartmentServiceTest {

    private ApartmentService apartmentService;
    private HotelFactory hotelFactory;


    @BeforeEach
    private void setup() {
        IdGenerator.cancelGenerateId();
        Config config = new Config();
        config.setConfigData(ConfigHandler.createConfig("src/main/resources/config/config-dev.yaml"));
        config.getConfigData().getDatabase().setPath("src/main/resources/json/testHotel.json");
        hotelFactory = new HotelFactory(config);
        apartmentService = hotelFactory.getApartmentService();
        apartmentService.save(1, 200.0);
        apartmentService.save(2, 350.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(3, 200.0);
    }

    @AfterEach
    private void teardown() {
        OnDiskApartmentDaoImpl onDiskApartmentDao = new OnDiskApartmentDaoImpl(hotelFactory);
        for (Apartment apartment : apartmentService.getAll()) {
            onDiskApartmentDao.remove(apartment.getId());
        }
    }


    @Test
    void given4Apartments_whenSortByID_thenGetCorrectListTest() {
        //given
        Apartment apartment1 = new Apartment(1, 1, 200.0, ApartmentStatus.AVAILABLE);
        Apartment apartment2 = new Apartment(2, 2, 350, ApartmentStatus.AVAILABLE);
        Apartment apartment3 = new Apartment(3, 4, 500, ApartmentStatus.AVAILABLE);
        Apartment apartment4 = new Apartment(4, 3, 200, ApartmentStatus.AVAILABLE);
        List<Apartment> expectedApartments = new ArrayList<>();
        expectedApartments.add(apartment1);
        expectedApartments.add(apartment2);
        expectedApartments.add(apartment3);
        expectedApartments.add(apartment4);
        //when
        List<Apartment> actualApartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.ID);
        //then
        assertEquals(expectedApartments, actualApartments);
    }


    @Test
    void given4Apartments_whenSortByCapacity_thenGetCorrectListTest() {
        //given
        Apartment apartment1 = new Apartment(1, 1, 200.0, ApartmentStatus.AVAILABLE);
        Apartment apartment2 = new Apartment(2, 2, 350.0, ApartmentStatus.AVAILABLE);
        Apartment apartment3 = new Apartment(3, 4, 500.0, ApartmentStatus.AVAILABLE);
        Apartment apartment4 = new Apartment(4, 3, 200.0, ApartmentStatus.AVAILABLE);
        List<Apartment> expectedApartments = new ArrayList<>();
        expectedApartments.add(apartment1);
        expectedApartments.add(apartment2);
        expectedApartments.add(apartment4);
        expectedApartments.add(apartment3);
        //when
        List<Apartment> actualApartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY);
        //then
        assertEquals(expectedApartments, actualApartments);
    }


    @Test
    void given4Apartments_whenSortByPrice_thenGetCorrectListTest() {
        //given
        Apartment apartment1 = new Apartment(1, 1, 200.0, ApartmentStatus.AVAILABLE);
        Apartment apartment2 = new Apartment(2, 2, 350.0, ApartmentStatus.AVAILABLE);
        Apartment apartment3 = new Apartment(3, 4, 500.0, ApartmentStatus.AVAILABLE);
        Apartment apartment4 = new Apartment(4, 3, 150.0, ApartmentStatus.AVAILABLE);
        List<Apartment> expectedApartments = new ArrayList<>();
        expectedApartments.add(apartment4);
        expectedApartments.add(apartment1);
        expectedApartments.add(apartment2);
        expectedApartments.add(apartment3);
        //when
        apartmentService.changePrice(4, 150.0);
        List<Apartment> actualApartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.PRICE);
        //then
        assertEquals(expectedApartments, actualApartments);
    }


    @Test
    void given4Apartments_whenSortByStatus_thenGetCorrectListTest() {
        //given
        Apartment apartment1 = new Apartment(1, 1, 200.0, ApartmentStatus.UNAVAILABLE);
        Apartment apartment2 = new Apartment(2, 2, 350.0, ApartmentStatus.UNAVAILABLE);
        Apartment apartment3 = new Apartment(3, 4, 500.0, ApartmentStatus.UNAVAILABLE);
        Apartment apartment4 = new Apartment(4, 3, 200.0, ApartmentStatus.AVAILABLE);

        List<Apartment> expectedApartments = new ArrayList<>();
        expectedApartments.add(apartment4);
        expectedApartments.add(apartment1);
        expectedApartments.add(apartment2);
        expectedApartments.add(apartment3);
        //when
        apartmentService.changeStatus(1);
        apartmentService.changeStatus(2);
        apartmentService.changeStatus(3);
        List<Apartment> actualApartments = apartmentService.getSorted(ApartmentService.ApartmentSortType.STATUS);
        //then
        assertEquals(expectedApartments, actualApartments);
    }
}
