package com.andersenlab.dao.JDBCImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.onDiskImpl.StateEntity;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcApartmentDaoImpl implements ApartmentDao {


    private static final String URL = "jdbc:postgresql://localhost:5432/hotel_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "r00t";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Apartment> getById(long id) {
        Apartment apartment;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM Apartment WHERE id = ?");

            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            apartment = new Apartment();
            apartment.setId(resultSet.getLong("id"));
            apartment.setPrice(resultSet.getDouble("price"));
            apartment.setCapacity(resultSet.getInt("capacity"));
            apartment.setStatus(ApartmentStatus.valueOf(resultSet.getString("status")));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(apartment);
    }

    @Override
    public List<Apartment> getAll() {
        List<Apartment> apartments = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Apartment");
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Apartment apartment = new Apartment();
                apartment.setId(resultSet.getLong("apartment_id"));
                apartment.setCapacity(resultSet.getInt("capacity"));
                apartment.setPrice(resultSet.getDouble("price"));
                apartment.setStatus(ApartmentStatus.valueOf(resultSet.getString("status")));
                apartments.add(apartment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return apartments;
    }

    @Override
    public Apartment save(Apartment apartment) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into apartment (capacity, price, status) values (?, ?, ?)");

            preparedStatement.setInt(1, apartment.getCapacity());
            preparedStatement.setDouble(2, apartment.getPrice());
            preparedStatement.setString(3, String.valueOf(apartment.getStatus()));

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return apartment;
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
        try {
            if (apartment.getPrice() != 0.0) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Apartment SET price=? WHERE apartment_id=?");
                preparedStatement.setDouble(1, apartment.getPrice());
                preparedStatement.setLong(2, apartment.getId());
                preparedStatement.executeUpdate();
            }
            if (apartment.getCapacity() != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Apartment SET capacity=? WHERE apartment_id=?");
                preparedStatement.setInt(1, apartment.getCapacity());
                preparedStatement.setLong(2, apartment.getId());
                preparedStatement.executeUpdate();
            }
            if (apartment.getStatus() != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Apartment SET status=? WHERE apartment_id=?");
                preparedStatement.setString(1, String.valueOf(apartment.getStatus()));
                preparedStatement.setLong(2, apartment.getId());
                preparedStatement.executeUpdate();
                System.out.println("!!! Changing Status !!!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(apartment);
    }

    @Override
    public boolean remove(long id) {
        int answer = 0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Apartment WHERE apartment_id=?");
            preparedStatement.setLong(1, id);

            answer = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return (answer == 0) ? false : true;
    }
}
