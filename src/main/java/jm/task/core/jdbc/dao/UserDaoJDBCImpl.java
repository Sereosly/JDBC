package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    @Override
    public void createUsersTable() {
        final String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "age SMALLINT" +
                ")";
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей создана.");
        } catch (final SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        final String sql = "DROP TABLE IF EXISTS users";
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей удалена.");
        } catch (final SQLException e) {
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        final String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("Пользователь %s %s добавлен.%n", name, lastName);
        } catch (final SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        final String sql = "DELETE FROM users WHERE id = ?";
        try (final PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            final int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Пользователь с ID %d удалён.%n", id);
            } else {
                System.out.printf("Пользователь с ID %d не найден.%n", id);
            }
        } catch (final SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        final List<User> users = new ArrayList<>();
        final String sql = "SELECT * FROM users";
        try (final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                users.add(new User(resultSet));
            }
        } catch (final SQLException e) {
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        final String sql = "TRUNCATE TABLE users";
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей очищена.");
        } catch (final SQLException e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }
    }
}
