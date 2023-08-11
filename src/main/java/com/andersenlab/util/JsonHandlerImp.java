package com.andersenlab.util;

import com.andersenlab.config.Config;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class JsonHandlerImp implements JsonHandler {
        ClientService clientService;
        ApartmentService apartmentService;
        PerkService perkService;
        String pathJson = Config.INSTANCE.getConfigData().getDatabase().getPath();
    public JsonHandlerImp(HotelFactory hotelFactory) {
        clientService = hotelFactory.getClientService();
        apartmentService = hotelFactory.getApartmentService();
        perkService = hotelFactory.getPerkService();
    }

    @Override
    public void save() {
        State state = new State(apartmentService.getAll(), clientService.getAll(), perkService.getAll());
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathJson), state);

            } catch (IOException e) {
                throw new RuntimeException("There is a problem with outgoing files");
        }
    }

    @Override
    public void load() {
        if (checkIfExistsJson()) {
            State state;
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            try  {
                var reader = Files.newBufferedReader(Path.of(pathJson));
                state = objectMapper.readValue(reader, State.class);
            } catch (IOException e) {
                throw new RuntimeException("There is a problem with incoming files");
            }

            clientService.save(state.clientsList());
            apartmentService.save(state.apartmentsList());
            perkService.save(state.perksList());
        }
    }

    @Override
    public Boolean checkIfExistsJson() {
        return  new File(pathJson).exists();
    }
}

record State (List<Apartment> apartmentsList, List<Client> clientsList, List<Perk> perksList) {
}
