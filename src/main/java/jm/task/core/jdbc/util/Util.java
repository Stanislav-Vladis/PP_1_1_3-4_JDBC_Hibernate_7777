package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    // реализуйте настройку соеденения с БД

    public static Connection getConnection() throws SQLException {

        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(Paths.get("src", "main", "resources", "database.properties"))) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String connectionUrl = properties.getProperty("connectionUrl");
        String userName = properties.getProperty("userName");
        String password = properties.getProperty("password");

        return DriverManager.getConnection(connectionUrl, userName, password);

    }

}
