package com.andersenlab.service;

import com.andersenlab.dao.impl.ApartmentDaoImpl;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;



public class ApartmentServiceTest {
    private static ApartmentService apartmentService;

    @BeforeAll
    static void setup() {
        ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
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
    public void save_ReturnedNotNullTest() {
        ApartmentDaoImpl apartmentDao = new ApartmentDaoImpl();
        ApartmentServiceImpl apartmentService1 = new ApartmentServiceImpl(apartmentDao);
        Assertions.assertAll("apartment",
                () -> {
                    Apartment apartment = apartmentService1.save(101,1,500);
                    Assertions.assertNotNull(apartment);
                    Assertions.assertEquals(101, apartment.getApartmentNumber());
                }
        );
    }

    @Test
    public void getById_ReturnNotNullTest() {
        Assertions.assertAll("apartment",
                () -> {
                    long id = apartmentService.getById(0).getId();
                    Assertions.assertNotNull(apartmentService.getById(0));
                },
                () -> Assertions.assertEquals(500, apartmentService.getById(0).getPrice()),
                () -> Assertions.assertEquals(1, apartmentService.getById(0).getCapacity()));
    }

    @Test
    public void changePrice_ReturnNewValueTest() {
        Assertions.assertEquals(1000, apartmentService.changePrice(0, 1000).getPrice());
    }

    @Test
    public void changeStatus_ReturnNewValueTest() {
        Assertions.assertEquals(ApartmentStatus.UNAVAILABLE, apartmentService.changeStatus(0, ApartmentStatus.UNAVAILABLE).getStatus());
    }

    @Test
    public void sortByCapacityTest() {
        Assertions.assertEquals(1, apartmentService.sortByCapacity().stream().findFirst().get().getCapacity());
    }

    @Test
    public void sortByStatusTest() {
        apartmentService.changeStatus(1, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(2, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(3, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(4, ApartmentStatus.UNAVAILABLE);
        apartmentService.changeStatus(5, ApartmentStatus.UNAVAILABLE);
        Assertions.assertEquals(ApartmentStatus.AVAILABLE, apartmentService.sortByStatus().stream().findFirst().get().getStatus());
    }

    @Test
    public void sortByPriceTest() {
        Assertions.assertEquals(200, apartmentService.sortByPrice().stream().findFirst().get().getPrice());
    }
}
