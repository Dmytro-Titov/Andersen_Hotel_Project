package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.impl.ApartmentDaoImpl;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApartmentServiceTest {

    protected static ApartmentDao apartmentDao = new ApartmentDaoImpl();
    protected static ApartmentService apartmentService = new ApartmentServiceImpl(apartmentDao);


    @BeforeAll
    static void setup() {
        apartmentService.save(1, 200.0);
        apartmentService.save(2, 350.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(0, 200.0);
        apartmentService.save(1, 200.0);
        apartmentService.save(4, 500.0);
        apartmentService.save(2, 350.0);
    }


    @Test
    synchronized void getSortedTest() {
        apartmentService.changePrice(5, 150.0);
        apartmentService.changeStatus(1);
        apartmentService.changeStatus(2);
        apartmentService.changeStatus(3);
        apartmentService.changeStatus(4);
        apartmentService.changeStatus(5);
        apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY).forEach(apartment ->
                System.out.println(apartment.getId() + "-"+ apartment.getCapacity() + " - " + apartment.getPrice()));
        assertEquals(1, apartmentService.getSorted(ApartmentService.ApartmentSortType.ID)
                .stream().findFirst().get().getId());
        assertEquals(4, apartmentService.getSorted(ApartmentService.ApartmentSortType.CAPACITY)
                .stream().findFirst().get().getId());
        assertEquals(5, apartmentService.getSorted(ApartmentService.ApartmentSortType.PRICE)
                .stream().findFirst().get().getId());
        assertEquals(6, apartmentService.getSorted(ApartmentService.ApartmentSortType.STATUS)
                .stream().findFirst().get().getId());
    }
}
