package com.andersenlab.service;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.entity.Apartment;
import com.andersenlab.factory.HotelFactory;

import java.util.List;

public class ApartmentService {
    private final ApartmentDao apartmentDao = new ApartmentDao();

    public void run(String command) {
        String[] commandArray = command.trim().toLowerCase().split("\s");
        switch (commandArray[1]) {
            case "add":
                addApartment(Integer.parseInt(commandArray[2]));
                return;
            case "changestatus":
                changeApartmentStatus(Integer.parseInt(commandArray[2]));
                return;
            case "check-in":
                checkIn(Integer.parseInt(commandArray[2]), Integer.parseInt(commandArray[3]));
                return;
            case "get":
                getApartment(Integer.parseInt(commandArray[2]));
                return;
            case "getall":
                getAllApartments();
                return;
            //etc
        }

        System.out.println("error message");
    }

    private void addApartment(int capacity) {
        long id = apartmentDao.save(new Apartment(capacity));

        System.out.printf("Created apartment with id:%s and capacity:%s%n", id, capacity);
    }

    private void changeApartmentStatus(long id) {
        apartmentDao.getApartment(id).changeStatus();
    }

    private void checkIn(long clientId, long apartmentId) {
        HotelFactory.getInstance().getClientService().run("client changestatus %s %s".formatted(clientId, apartmentId));
    }

    private void getApartment(long id) {
        Apartment apartment = apartmentDao.getApartment(id);
        if (apartment != null) {
            System.out.println(apartment);
        } else {
            System.out.println("Apartment with this id does not exist");
        }
    }

    private void getAllApartments() {
        List<Apartment> list = apartmentDao.getAllApartments();
        for (Apartment apartment : list) {
            System.out.println(apartment);
        }
    }
}
