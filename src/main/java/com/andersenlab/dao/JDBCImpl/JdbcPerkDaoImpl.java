package com.andersenlab.dao.JDBCImpl;

import com.andersenlab.dao.PerkDao;
import com.andersenlab.dao.conection.ConnectionPool;
import com.andersenlab.entity.Perk;
import com.andersenlab.factory.HotelFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class JdbcPerkDaoImpl implements PerkDao {
    private final ConnectionPool connectionPool;

    public JdbcPerkDaoImpl(HotelFactory hotelFactory) {
        this.connectionPool = new ConnectionPool(hotelFactory.getConfig().getConfigData().getPostgresDatabase());
    }

    @Override
    public Optional<Perk> getById(long id) {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Perk WHERE perk_id = ?");
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Perk perk = new Perk();
                perk.setId(resultSet.getLong("perk_id"));
                perk.setName(String.valueOf(resultSet.getString("name")));
                perk.setPrice(resultSet.getDouble("price"));
                return Optional.of(perk);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Perk> getAll() {
        List<Perk> perks = new ArrayList<>();
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM Perk");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                var perk = new Perk();
                perk.setId(resultSet.getLong("perk_id"));
                perk.setName(String.valueOf(resultSet.getString("name")));
                perk.setPrice(resultSet.getDouble("price"));
                perks.add(perk);
            }
            return perks;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Perk save(Perk perk) {
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into perk (name, price) values (?, ?)");

            preparedStatement.setString(1, String.valueOf(perk.getName()));
            preparedStatement.setDouble(2, perk.getPrice());
            preparedStatement.executeUpdate();
            return perk;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Perk> update(Perk perk) {
        try (Connection connection = connectionPool.getConnection()) {
            if (perk.getPrice() != 0.0) {
                updateField(connection, perk.getId(), "price", perk.getPrice());
            }
            if (perk.getName() != null) {
                updateField(connection, perk.getId(), "name", perk.getName());
            }
            return Optional.of(perk);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateField(Connection connection, long perkId, String fieldName, Object value) throws SQLException {
        String query = "UPDATE Perk SET " + fieldName + "=? WHERE perk_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, value);
            preparedStatement.setLong(2, perkId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public boolean remove(long id) {
        int answer;
        try {
            Connection connection = connectionPool.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement("DELETE FROM Perk WHERE perk_id=?");
            preparedStatement.setLong(1, id);

            answer = preparedStatement.executeUpdate();
            return answer != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Perk> getSortedBy(PerkSortType type) {
        return switch (type) {
            case ID -> sortBy(Perk::getId);
            case NAME -> sortBy(Perk::getName);
            case PRICE -> sortBy(Perk::getPrice);
        };
    }

    private List<Perk> sortBy(Function<Perk, Comparable> extractor) {
        return getAll().stream()
                .sorted(Comparator.comparing(extractor))
                .toList();
    }
}
