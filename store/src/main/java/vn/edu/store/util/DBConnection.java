package vn.edu.store.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = DBConnection.class.getClassLoader().getResourceAsStream("database.properties")) {
            if (inputStream != null) {
                properties.load(inputStream);
                URL = properties.getProperty("db.url");
                USER = properties.getProperty("db.username");
                PASSWORD = properties.getProperty("db.password");
            } else {
                throw new RuntimeException("Khong tim thay file database.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Loi khi doc file database.properties", e);
        }
    }

    private DBConnection() {
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Khong tim thay MariaDB JDBC Driver", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
