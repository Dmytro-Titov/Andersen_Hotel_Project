package com.andersenlab.factory;

import com.andersenlab.config.Config;
import com.andersenlab.dao.JDBCImpl.JdbcApartmentDaoImpl;
import com.andersenlab.dao.JDBCImpl.JdbcClientDaoImpl;
import com.andersenlab.dao.JDBCImpl.JdbcPerkDaoImpl;
import com.andersenlab.dao.conection.ConnectionPool;
import com.andersenlab.dao.hibernateImpl.HibernateApartmentDaoImpl;
import com.andersenlab.dao.hibernateImpl.HibernateClientDaoImpl;
import com.andersenlab.dao.hibernateImpl.HibernatePerkDaoImpl;
import com.andersenlab.dao.inMemoryImpl.InMemoryApartmentDaoImpl;
import com.andersenlab.dao.inMemoryImpl.InMemoryClientDaoImpl;
import com.andersenlab.dao.inMemoryImpl.InMemoryPerkDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskApartmentDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskClientDaoImpl;
import com.andersenlab.dao.onDiskImpl.OnDiskJsonHandler;
import com.andersenlab.dao.onDiskImpl.OnDiskPerkDaoImpl;
import com.andersenlab.service.*;
import com.andersenlab.service.impl.*;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Component;

@Component
public class HotelFactory {

    private final ClientService clientService;
    private final ApartmentService apartmentService;
    private final PerkService perkService;
    private final Config config;

   private final OnDiskJsonHandler onDiskJsonHandler;

    public HotelFactory(Config config) {
        this.config = config;
        onDiskJsonHandler = new OnDiskJsonHandler(config.getConfigData().getDatabase().getPath());
        switch (this.config.getConfigData().getSaveOption()) {
            case DISK -> {


                apartmentService = new ApartmentServiceImpl(new OnDiskApartmentDaoImpl(onDiskJsonHandler), this);
                perkService = new PerkServiceImpl(new OnDiskPerkDaoImpl(onDiskJsonHandler), this);
                clientService = new ClientServiceImpl(new OnDiskClientDaoImpl(onDiskJsonHandler), this);
            }
            case JDBC -> {
                ConnectionPool connectionPool = new ConnectionPool(config.getConfigData().getPostgresDatabase());

                apartmentService = new ApartmentServiceImpl(new JdbcApartmentDaoImpl(connectionPool), this);
                perkService = new PerkServiceImpl(new JdbcPerkDaoImpl(connectionPool), this);
                clientService = new ClientServiceImpl(new JdbcClientDaoImpl(connectionPool), this);
            }
            case HIBERNATE -> {
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hotel");
                apartmentService = new ApartmentServiceImpl(new HibernateApartmentDaoImpl(entityManagerFactory), this);
                perkService = new PerkServiceImpl(new HibernatePerkDaoImpl(entityManagerFactory), this);
                clientService = new ClientServiceImpl(new HibernateClientDaoImpl(entityManagerFactory), this);
            }
            default -> {
                this.apartmentService = new ApartmentServiceImpl(new InMemoryApartmentDaoImpl(), this);
                this.perkService = new PerkServiceImpl(new InMemoryPerkDaoImpl(), this);
                this.clientService = new ClientServiceImpl(new InMemoryClientDaoImpl(), this);
            }
        }
    }

    public ApartmentService getApartmentService() {
        return apartmentService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public PerkService getPerkService() {
        return perkService;
    }

    public Config getConfig() {
        return config;
    }

    public OnDiskJsonHandler getOnDiskJsonHandler() {
        return onDiskJsonHandler;
    }
}
