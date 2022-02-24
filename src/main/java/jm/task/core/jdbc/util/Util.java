package jm.task.core.jdbc.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final String userName;
    private final String password;
    private final String connectionUrl;

    public Util(String userName, String password, String connectionUrl) {
        this.userName = userName;
        this.password = password;
        this.connectionUrl = connectionUrl;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, userName, password);
    }

}
