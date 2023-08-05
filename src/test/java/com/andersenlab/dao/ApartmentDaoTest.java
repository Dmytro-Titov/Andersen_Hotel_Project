package com.andersenlab.dao;

import com.andersenlab.dao.impl.ApartmentDaoImpl;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import com.andersenlab.util.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ApartmentDaoTest {

        static private Apartment apartment;
        private static ApartmentService apartmentService;
        private static ApartmentDaoImpl apartmentDao;

        @BeforeAll
        private static void setup() {
                apartmentDao = new ApartmentDaoImpl();
                apartmentService = new ApartmentServiceImpl(apartmentDao);
                apartmentService.save(1, 1, 500.0);
                apartmentService.save(2, 2, 350.0);
                apartmentService.save(3, 4, 500.0);
        }

        @Test
        public void getByIDTest() {
                Assertions.assertEquals(0, apartmentDao.getById(0).get().getId());
                Assertions.assertTrue(apartmentDao.getById(IdGenerator.generateApartmentId()+1).isEmpty());
        }

        @Test
        public void saveTest() {
                apartment = new Apartment();
                apartment.setId(IdGenerator.generateApartmentId());
                apartment.setApartmentNumber(101);
                apartment.setCapacity(1);
                apartment.setPrice(200);
                apartment.setStatus(ApartmentStatus.AVAILABLE);

                Assertions.assertEquals(apartment, apartmentDao.save(apartment));
                Assertions.assertEquals(apartment.getId(), apartmentDao.getById(apartment.getId()).get().getId());
        }

        @Test
        public void getAll() {
                Assertions.assertNotNull(apartmentDao.getAll());
                Assertions.assertEquals(0, apartmentDao.getAll().get(0).getId());
        }

        @Test
        public void updateTest() {
                apartment = new Apartment();
                apartment.setId(0);
                apartment.setApartmentNumber(101);
                apartment.setCapacity(5);
                apartment.setPrice(1000);
                apartment.setStatus(ApartmentStatus.UNAVAILABLE);

                Assertions.assertAll("apartmentUpdate",
                        () -> Assertions.assertNotNull(apartmentDao.update(apartment)),
                        () -> Assertions.assertEquals(0,apartmentDao.update(apartment).get().getId()),
                        () -> Assertions.assertEquals(101,apartmentDao.update(apartment).get().getApartmentNumber()),
                        () -> Assertions.assertEquals(5,apartmentDao.update(apartment).get().getCapacity()),
                        () -> Assertions.assertEquals(1000,apartmentDao.update(apartment).get().getPrice()),
                        () -> Assertions.assertEquals(ApartmentStatus.UNAVAILABLE,apartmentDao.update(apartment).get().getStatus()),
                        () -> {
                                apartment.setPrice(0.0);
                                Assertions.assertEquals(1000, apartmentDao.update(apartment).get().getPrice());
                        },
                        () -> {
                                apartment.setPrice(1000);
                                Apartment apartment2 = new Apartment();
                                Assertions.assertEquals(apartment, apartmentDao.update(apartment2).get());

                        }
                        );
        }

        @Test
        public void removeTest() {
                Assertions.assertTrue(apartmentDao.remove(0));
                Assertions.assertTrue(apartmentDao.getById(0).isEmpty());
        }
}
