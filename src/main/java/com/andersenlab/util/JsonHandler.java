package com.andersenlab.util;

import com.andersenlab.config.ConfigData;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.*;
import java.util.*;

public class JsonHandler {
        ClientService clientService;
        ApartmentService apartmentService;
        PerkService perkService;
        ConfigData configData = ConfigHandler.createConfig("");
    public JsonHandler(HotelFactory hotelFactory) {
        clientService = hotelFactory.getClientService();
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();
    }

    public void save() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            objectMapper.writeValue(new File(configData.getDatabase().getPath()+"client.json"), clientService.getAll());
            objectMapper.writeValue(new File(configData.getDatabase().getPath()+"apartment.json"), apartmentService.getAll());
            objectMapper.writeValue(new File(configData.getDatabase().getPath()+"perk.json"), perkService.getAll());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void load() {
        List<Client> clients;
        List<Apartment> apartments;
        List<Perk> perks;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String path = configData.getDatabase().getPath();

        try {
            clients = objectMapper.readValue(new File(path+"client.json"), new TypeReference<List<Client>>() {});
            apartments = objectMapper.readValue(new File(path+"apartment.json"), new TypeReference<List<Apartment>>() {});
            perks = objectMapper.readValue(new File(path+"perk.json"), new TypeReference<List<Perk>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        clientService.save(clients);
        apartmentService.save(apartments);
        perkService.save(perks);
    }
}
