package com.andersenlab.util;

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

public class JsonHandlerImp implements JsonHandler {
        ClientService clientService;
        ApartmentService apartmentService;
        PerkService perkService;
        String pathJson;
    public JsonHandlerImp(HotelFactory hotelFactory) {
        clientService = hotelFactory.getClientService();
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();
        pathJson = hotelFactory.getConfig().getConfigData().getDatabase().getPath();
    }

    @Override
    public void save() {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            try {
                objectMapper.writeValue(new File(pathJson+"client.json"), clientService.getAll());
                objectMapper.writeValue(new File(pathJson+"apartment.json"), apartmentService.getAll());
                objectMapper.writeValue(new File(pathJson+"perk.json"), perkService.getAll());
            } catch (IOException e) {
                throw new RuntimeException("There is a problem with outgoing files");
        }
    }

    @Override
    public void load() {
        if (checkIfExistsJson()) {
            List<Client> clients;
            List<Apartment> apartments;
            List<Perk> perks;

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            try {
                clients = objectMapper.readValue(new File(pathJson + "client.json"), new TypeReference<>() {});
                apartments = objectMapper.readValue(new File(pathJson + "apartment.json"), new TypeReference<>() {});
                perks = objectMapper.readValue(new File(pathJson + "perk.json"), new TypeReference<>() {});
            } catch (IOException e) {
                throw new RuntimeException("There is a problem with incoming files");
            }

            clientService.save(clients);
            apartmentService.save(apartments);
            perkService.save(perks);
        }
    }

    @Override
    public Boolean checkIfExistsJson() {
        return new File(pathJson + "client.json").exists()
                && new File(pathJson + "apartment.json").exists()
                && new File(pathJson + "perk.json").exists();

    }
}
