package com.andersenlab.cleandb;

import com.andersenlab.config.SaveOption;
import com.andersenlab.dao.conection.ConnectionPool;
import com.andersenlab.dao.onDiskImpl.OnDiskJsonHandler;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CleanPerkTable {

    private final HotelFactory hotelFactory;

    public CleanPerkTable(HotelFactory hotelFactory) {
        this.hotelFactory = hotelFactory;
    }

    public void cleanTable() {
        if (hotelFactory.getConfig().getConfigData().getSaveOption() == SaveOption.HIBERNATE) {
            Configuration configuration = new Configuration().addAnnotatedClass(Perk.class)
                    .addAnnotatedClass(Apartment.class).addAnnotatedClass(Client.class);
            SessionFactory sessionFactory = configuration.buildSessionFactory();
            try (Session session = sessionFactory.getCurrentSession()) {
                session.beginTransaction();
                session.createQuery("DELETE FROM Perk").executeUpdate();
                session.getTransaction().commit();
            }
        } else if (hotelFactory.getConfig().getConfigData().getSaveOption() == SaveOption.DISK) {
            OnDiskJsonHandler onDiskJsonHandler = new OnDiskJsonHandler(hotelFactory);
            var stateEntity = onDiskJsonHandler.load();
            var perks = stateEntity.getPerksList();
            var copy = new ArrayList<>(perks);
            copy.removeAll(perks);
            onDiskJsonHandler.save(stateEntity.addPerkList(copy));
        } else if (hotelFactory.getConfig().getConfigData().getSaveOption() == SaveOption.JDBC) {
            try {
                ConnectionPool connectionPool = new ConnectionPool(hotelFactory.getConfig().getConfigData().getPostgresDatabase());
                Connection connection = connectionPool.getConnection();
                connection.prepareStatement("DELETE * FROM Perk").executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else if (hotelFactory.getConfig().getConfigData().getSaveOption() == SaveOption.MEMORY) {
            List<Perk> perks = hotelFactory.getPerkService().getAll();
            for (Perk perk : perks) {
                perks.remove(perk);
            }
        }
    }
}
