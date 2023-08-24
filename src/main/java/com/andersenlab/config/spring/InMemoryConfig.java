package com.andersenlab.config.spring;


import com.andersenlab.config.Config;
import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.inMemoryImpl.InMemoryApartmentDaoImpl;
import com.andersenlab.dao.inMemoryImpl.InMemoryClientDaoImpl;
import com.andersenlab.dao.inMemoryImpl.InMemoryPerkDaoImpl;
import com.andersenlab.service.ApartmentService;
import com.andersenlab.service.ClientService;
import com.andersenlab.service.PerkService;
import com.andersenlab.service.impl.ApartmentServiceImpl;
import com.andersenlab.service.impl.ClientServiceImpl;
import com.andersenlab.service.impl.PerkServiceImpl;
import org.springframework.context.annotation.Bean;

public class InMemoryConfig {

    private Config config;

    private PerkDao perkDao;
    private ApartmentDao apartmentDao;

    private ClientDao clientDao;

    private ApartmentService apartmentService;
    private PerkService perkService;
    private ClientService clientService;

    @Bean
    Config getConfig() {
        return new Config("src/main/resources/config/config-dev.yaml");
    }

    @Bean
    ClientDao getClientDao() {
        return new InMemoryClientDaoImpl();
    }

    @Bean
    ApartmentDao getApartmentDao() {
        return new InMemoryApartmentDaoImpl();
    }

    @Bean
    PerkDao getPerkDao() {
        return new InMemoryPerkDaoImpl();
    }

    @Bean
    PerkService getPerkService() {
        return new PerkServiceImpl(perkDao);
    }

    @Bean
    ApartmentService getApartmentService() {
        return new ApartmentServiceImpl(apartmentDao, config);
    }

    @Bean
    ClientService getClientService() {
        return new ClientServiceImpl(clientDao, apartmentService, perkService);
    }
}
