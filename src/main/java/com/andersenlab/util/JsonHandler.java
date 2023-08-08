package com.andersenlab.util;

import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.*;

public class JsonHandler {
    HotelFactory hotelFactory;
    ClientService clientService = hotelFactory.getClientService();
    ApartmentService apartmentService = hotelFactory.getApartmentService();
    PerkService perkService = hotelFactory.getPerkService();

    public void save() {

        List<List<Object>> lists = new ArrayList<>();
        lists.add(Collections.singletonList(clientService.getAll()));
        lists.add(Collections.singletonList(apartmentService.getAll()));
        lists.add(Collections.singletonList(perkService.getAll()));

        FileInputStream inputStream;
        Properties property = new Properties();
        String path;

        try {
            inputStream = new FileInputStream("src/main/resources/config.properties");
            property.load(inputStream);

            path = property.getProperty("path");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(path), lists);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
