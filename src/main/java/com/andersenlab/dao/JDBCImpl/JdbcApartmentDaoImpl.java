package com.andersenlab.dao.JDBCImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.conection.ConnectionPool;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.ApartmentStatus;
import com.andersenlab.factory.HotelFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JdbcApartmentDaoImpl implements ApartmentDao {
    private final ConnectionPool connectionPool;

    public JdbcApartmentDaoImpl(HotelFactory hotelFactory) {
        this.connectionPool = new ConnectionPool(hotelFactory.getConfig().getConfigData().getPostgresDatabase());
    }

    @Override
    public Optional<Apartment> getById(long id) {
        Apartment apartment;
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Apartment WHERE apartment_id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                apartment = new Apartment();
                apartment.setId(resultSet.getLong("apartment_id"));
                apartment.setCapacity(resultSet.getInt("capacity"));
                apartment.setPrice(resultSet.getDouble("price"));
                apartment.setStatus(ApartmentStatus.valueOf(resultSet.getString("status")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(apartment);
    }

    @Override
    public List<Apartment> getAll() {
        List<Apartment> apartments = new ArrayList<>();

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Apartment");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
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
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into apartment (capacity, price, status) values (?, ?, ?)");

            preparedStatement.setInt(1, apartment.getCapacity());
            preparedStatement.setDouble(2, apartment.getPrice());
            preparedStatement.setString(3, String.valueOf(apartment.getStatus()));
            preparedStatement.executeUpdate();

            apartment.setId(getApartmentLastId());
            return apartment;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Apartment> update(Apartment apartment) {
//        try {
//            if (apartment.getPrice() != 0.0) {
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement preparedStatement = connection
//                        .prepareStatement("UPDATE Apartment SET price=? WHERE apartment_id=?");
//                preparedStatement.setDouble(1, apartment.getPrice());
//                preparedStatement.setLong(2, apartment.getId());
//                preparedStatement.executeUpdate();
//            }
//            if (apartment.getCapacity() != 0) {
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement preparedStatement = connection
//                        .prepareStatement("UPDATE Apartment SET capacity=? WHERE apartment_id=?");
//                preparedStatement.setInt(1, apartment.getCapacity());
//                preparedStatement.setLong(2, apartment.getId());
//                preparedStatement.executeUpdate();
//            }
//            if (apartment.getStatus() != null) {
//                Connection connection = connectionPool.getConnection();
//                PreparedStatement preparedStatement = connection
//                        .prepareStatement("UPDATE Apartment SET status=? WHERE apartment_id=?");
//                preparedStatement.setString(1, String.valueOf(apartment.getStatus()));
//                preparedStatement.setLong(2, apartment.getId());
//                preparedStatement.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return Optional.of(apartment);
//    }
        try (Connection connection = connectionPool.getConnection()) {
            if (apartment.getPrice() != 0.0) {
                updateField(connection, apartment.getId(), "price", apartment.getPrice());
            }
            if (apartment.getCapacity() != 0) {
                updateField(connection, apartment.getId(), "capacity", apartment.getCapacity());
            }
            if (apartment.getStatus() != null) {
                updateField(connection, apartment.getId(), "status", String.valueOf(apartment.getStatus()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(apartment);
    }

    private void updateField(Connection connection, long apartmentId, String fieldName, Object value) throws SQLException {
        String query = "UPDATE Apartment SET " + fieldName + "=? WHERE apartment_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setLong(2, apartmentId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean remove(long id) {
        int answer;

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM Apartment WHERE apartment_id=?");
            preparedStatement.setLong(1, id);

            answer = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return answer != 0;
    }


    @Override
    public List<Apartment> getSortedBy(ApartmentSortType type) {
        return switch (type) {
            case ID -> sortBy(Apartment::getId);
            case PRICE -> sortBy(Apartment::getPrice);
            case CAPACITY -> sortBy(Apartment::getCapacity);
            case STATUS -> sortBy(Apartment::getStatus);
        };
    }

    private List<Apartment> sortBy(Function<Apartment, Comparable> extractor) {
        return getAll().stream()
                .sorted(Comparator.comparing(extractor))
                .toList();
    }

    private int getApartmentLastId() {
        String query = "SELECT MAX(apartment_id) FROM apartment";
        int lastId = 0;
        try (Connection connection = connectionPool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                lastId = resultSet.getInt(1);
            }

            return lastId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
