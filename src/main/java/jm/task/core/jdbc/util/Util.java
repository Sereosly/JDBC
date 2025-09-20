package jm.task.core.jdbc.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_URL;
    private static final String DB_USERNAME;
    private static final String DB_PASSWORD;

    static {
        try (final InputStream input = Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            final Properties props = new Properties();
            props.load(input);
            DB_URL = props.getProperty("db.url");
            DB_USERNAME = props.getProperty("db.username");
            DB_PASSWORD = props.getProperty("db.password");
        } catch (final Exception e) {
            throw new RuntimeException("FAILED CONFIG", e);
        }
    }
    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (final ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            final Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to database successfully");
            return connection;
        } catch (final SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
