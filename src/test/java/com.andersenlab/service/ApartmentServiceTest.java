package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.impl.ApartmentDaoImpl;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApartmentServiceTest {

    private static ApartmentService apartmentService;

    @BeforeAll
    static void setup() {
        ApartmentDao apartmentDao = new ApartmentDaoImpl();
        apartmentService = new ApartmentServiceImpl(apartmentDao);
        apartmentService.save(1, 1, 500.0);
        apartmentService.save(2, 2, 350.0);
        apartmentService.save(3, 4, 500.0);
        apartmentService.save(4, 1, 200.0);
        apartmentService.save(5, 1, 200.0);
        apartmentService.save(6, 4, 500.0);
        apartmentService.save(7, 2, 350.0);
    }


    @Test
    void sortedByPriceTest() {
        apartmentService.changePrice(4, 150.0);
        assertEquals(4, apartmentService.sortByPrice().stream().findFirst().get().getApartmentNumber());
    }


    @Test
    void sortByStatusTest() {
        apartmentService.changeStatus(1, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(2, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(3, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(4, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(5, ApartmentStatus.UNAVAILABLE);
        assertEquals(6, apartmentService.sortByStatus().stream().findFirst().get().getApartmentNumber());
    }


    @Test
    void sortByCapacityTest() {
        assertEquals(1, apartmentService.sortByStatus().stream().findFirst().get().getCapacity());
    }
}
