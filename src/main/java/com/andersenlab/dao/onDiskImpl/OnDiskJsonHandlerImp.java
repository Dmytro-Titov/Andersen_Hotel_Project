package com.andersenlab.dao.onDiskImpl;

import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import com.andersenlab.util.IdGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public final class OnDiskJsonHandlerImp implements OnDiskJsonHandler {
    private final String pathJson;
    public OnDiskJsonHandlerImp(HotelFactory hotelFactory) {
        pathJson = hotelFactory.getConfig().getConfigData().getDatabase().getPath();
    }

    @Override
    public void save(StateEntity stateEntity) {
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
            IdGenerator.setGenerateId(stateEntity.clientsList().size(), stateEntity.apartmentsList().size(), stateEntity.perksList().size());

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

    StateEntity addPerkList(List<Perk> perks) {
        return new StateEntity(apartmentsList, clientsList, perks);
    }

    StateEntity addApartmentList(List<Apartment> apartments) {
        return new StateEntity(apartments, clientsList, perksList);
    }

    StateEntity addClientList(List<Client> clients) {
        return new StateEntity(apartmentsList, clients, perksList);
    }

}
