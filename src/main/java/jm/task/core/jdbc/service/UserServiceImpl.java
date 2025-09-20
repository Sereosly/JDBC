package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;

import java.util.List;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;

public class UserServiceImpl implements UserService {

    private final Connection connection;
    public UserServiceImpl() {
        this.connection = Util.getConnection();
    }

//    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS \"user\" (" +
                "id BIGSERIAL PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "lastName VARCHAR(50) NOT NULL," +
                "age SMALLINT" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей создана.");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

//    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS \"user\"";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы: " + e.getMessage());
        }
    }

//    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO \"user\" (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("Пользователь %s %s добавлен.%n", name, lastName);
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя: " + e.getMessage());
        }
    }

//    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM \"user\" WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Пользователь с ID %d удалён.%n", id);
            } else {
                System.out.printf("Пользователь с ID %d не найден.%n", id);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

//    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM \"user\"";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении пользователей: " + e.getMessage());
        }
        return users;
    }

//    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE \"user\"";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Таблица пользователей очищена.");
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы: " + e.getMessage());
        }

    }
}
