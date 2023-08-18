package com.andersenlab.dao.conection;

import com.andersenlab.config.ConfigData;
import com.andersenlab.config.ConfigData.PostgresDB;
import com.andersenlab.factory.HotelFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static HikariDataSource dataSource;
    private static ConnectionPool instance;

    private ConnectionPool() {
    }
    public static ConnectionPool getInstance(HotelFactory hotelFactory) {
        if (instance == null) {
            PostgresDB configDB = hotelFactory.getConfig().getConfigData().getPostgresDatabase();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(configDB.getUrl());
            config.setUsername(configDB.getUsername());
            config.setPassword(configDB.getPassword());
            config.setMaximumPoolSize(configDB.getPool());
            dataSource = new HikariDataSource(config);
            instance = new ConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void closeAllConnections() {
        dataSource.close();
    }

}
