package com.andersenlab.dao.JDBCImpl;

import com.andersenlab.dao.ApartmentDao;
import com.andersenlab.dao.ClientDao;
import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.conection.ConnectionPool;
import com.andersenlab.entity.Apartment;
import com.andersenlab.entity.Client;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcClientDaoImpl implements ClientDao {

    private final ConnectionPool connectionPool;
    private final ApartmentDao apartmentDao;
    private final PerkDao perkDao;

    public JdbcClientDaoImpl(HotelFactory hotelFactory) {
        this.connectionPool = new ConnectionPool(hotelFactory.getConfig().getConfigData().getPostgresDatabase());
        this.apartmentDao = new JdbcApartmentDaoImpl(hotelFactory);
        this.perkDao = new JdbcPerkDaoImpl(hotelFactory);
    }


    @Override
    public Optional<Client> getById(long id) {

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Client WHERE client_id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("client_id"));
                client.setName(String.valueOf(resultSet.getString("name")));
                Timestamp timestamp = resultSet.getTimestamp("checkin");
                if (timestamp != null) {
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    client.setCheckInDate(dateTime);
                }
                timestamp = resultSet.getTimestamp("checkout");
                if (timestamp != null) {
                    LocalDateTime dateTime = timestamp.toLocalDateTime();
                    client.setCheckOutDate(dateTime);
                }
                long apartmentId = resultSet.getLong("apartment_id");
                client.setApartment(apartmentDao.getById(apartmentId).orElse(null));

                List<Perk> perks = getPerksForClient(id);
                client.setPerks(perks);

                client.setStayCost(resultSet.getDouble("staycost"));
                client.setQuantityOfPeople(resultSet.getInt("quantityofpeople"));

                return Optional.of(client);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Perk> getPerksForClient(long clientId) {
        List<Perk> perks = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT p.* FROM Perk p " +
                    "INNER JOIN Client_Perk cp ON p.perk_id = cp.perk_id " +
                    "WHERE cp.client_id = ?");
            preparedStatement.setLong(1, clientId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Perk perk = new Perk();
                perk.setId(resultSet.getLong("perk_id"));
                perk.setName(resultSet.getString("name"));
                perk.setPrice(resultSet.getDouble("price"));
                perks.add(perk);
            }
            return perks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM Client");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("client_id"));
                client.setName(resultSet.getString("name"));

                Timestamp checkinTimestamp = resultSet.getTimestamp("checkin");
                if (checkinTimestamp != null) {
                    LocalDateTime checkinDateTime = checkinTimestamp.toLocalDateTime();
                    client.setCheckInDate(checkinDateTime);
                }

                Timestamp checkoutTimestamp = resultSet.getTimestamp("checkout");
                if (checkoutTimestamp != null) {
                    LocalDateTime checkoutDateTime = checkoutTimestamp.toLocalDateTime();
                    client.setCheckOutDate(checkoutDateTime);
                }

                long apartmentId = resultSet.getLong("apartment_id");
                client.setApartment(apartmentDao.getById(apartmentId).orElse(null));

                List<Perk> perks = getPerksForClient(client.getId());
                client.setPerks(perks);

                client.setStayCost(resultSet.getDouble("staycost"));
                client.setQuantityOfPeople(resultSet.getInt("quantityofpeople"));

                clients.add(client);
            }
            return clients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client save(Client client) {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO client (name, checkin, checkout, apartment_id, staycost, quantityofpeople) VALUES (?,?,?,?,?,?)");

            preparedStatement.setString(1, client.getName());

            if (client.getCheckInDate() != null) {
                Timestamp timestamp = Timestamp.valueOf(client.getCheckInDate());
                preparedStatement.setTimestamp(2, timestamp);
            } else {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            }

            if (client.getCheckOutDate() != null) {
                Timestamp timestamp = Timestamp.valueOf(client.getCheckOutDate());
                preparedStatement.setTimestamp(3, timestamp);
            } else {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            }

            if (client.getApartment() != null) {
                preparedStatement.setLong(4, client.getApartment().getId());
            } else {
                preparedStatement.setNull(4, Types.INTEGER);
            }

            preparedStatement.setDouble(5, client.getStayCost());
            preparedStatement.setInt(6, client.getQuantityOfPeople());

            preparedStatement.executeUpdate();
            return client;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Optional<Client> update(Client client) {
        List<Perk> perkList = client.getPerks();
        List<Perk> perkListFromDB = getPerksForClient(client.getId());

        if (client.getPerks().size() != perkListFromDB.size()) {
            for (int i = 0; i < perkList.size(); i++) {
                if (perkListFromDB.size() <= i || perkList.get(i).getId() != perkListFromDB.get(i).getId()) {
                    try (Connection connection = connectionPool.getConnection()) {
                        PreparedStatement preparedStatement = connection.prepareStatement(
                                "INSERT INTO client_perk (client_id, perk_id) VALUES (?, ?)");

                        preparedStatement.setLong(1, client.getId());
                        preparedStatement.setLong(2, perkList.get(i).getId());

                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        try (Connection connection = connectionPool.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE Client SET name=?, checkin=?, checkout=?, apartment_id=?, staycost=?, quantityofpeople=?" +
                            " WHERE client_id=?");

            preparedStatement.setString(1, client.getName());

            LocalDateTime checkinDateTime = client.getCheckInDate();
            if (checkinDateTime != null) {
                Timestamp checkinTimestamp = Timestamp.valueOf(checkinDateTime);
                preparedStatement.setTimestamp(2, checkinTimestamp);
            } else {
                preparedStatement.setNull(2, Types.TIMESTAMP);
            }

            LocalDateTime checkoutDateTime = client.getCheckOutDate();
            if (checkoutDateTime != null) {
                Timestamp checkoutTimestamp = Timestamp.valueOf(checkoutDateTime);
                preparedStatement.setTimestamp(3, checkoutTimestamp);
            } else {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            }

            Apartment apartment = client.getApartment();
            if (apartment != null) {
                preparedStatement.setLong(4, apartment.getId());
            } else {
                preparedStatement.setNull(4, Types.BIGINT);
            }

            preparedStatement.setDouble(5, client.getStayCost());
            preparedStatement.setInt(6, client.getQuantityOfPeople());

            preparedStatement.setLong(7, client.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(client);
    }

    public void addPerkToClient(long clientId, long perkId) {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO Client_Perk (client_id, perk_id) VALUES (?, ?)");

            preparedStatement.setLong(1, clientId);
            preparedStatement.setLong(2, perkId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(long id) {

        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM Client WHERE client_id=?");

            int answer = preparedStatement.executeUpdate();
            return answer != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
