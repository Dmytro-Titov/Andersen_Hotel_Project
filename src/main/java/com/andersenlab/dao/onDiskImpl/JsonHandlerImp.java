package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.config.Config;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class JsonHandlerImp implements JsonHandler {
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
        StateEntity stateEntity = new StateEntity(apartmentService.getAll(), clientService.getAll(), perkService.getAll());
        ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(pathJson), stateEntity);

            } catch (IOException e) {
                throw new RuntimeException("There is a problem with outgoing files");
        }
    }

    @Override
    public StateEntity load() {
        StateEntity stateEntity = new StateEntity();
        if (checkIfExistsJson()) {
            ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();
            try  {
                var reader = Files.newBufferedReader(Path.of(pathJson));
                stateEntity = objectMapper.readValue(reader, StateEntity.class);
            } catch (IOException e) {
                throw new RuntimeException("There is a problem with incoming files");
            }

            clientService.save(stateEntity.clientsList());
            apartmentService.save(stateEntity.apartmentsList());
            perkService.save(stateEntity.perksList());

            return stateEntity;
        }
        return stateEntity;
    }

    @Override
    public Boolean checkIfExistsJson() {
        return  new File(pathJson).exists();
    }
}

record StateEntity(List<Apartment> apartmentsList, List<Client> clientsList, List<Perk> perksList) {

    StateEntity() {
        this(List.of(), List.of(), List.of());
    }

    StateEntity(List<Apartment> apartmentsList, List<Client> clientsList, List<Perk> perksList) {
        this.apartmentsList = apartmentsList;
        this.clientsList = clientsList;
        this.perksList = perksList;
    }
}
