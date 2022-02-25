package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Данный класс реализует все функции необходимые для извлечения, обновления и удаления объектов User
public class UserDaoJDBCImpl implements UserDao {
    private Connection connection;

    public UserDaoJDBCImpl() {

        try {
            connection = Util.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void createUsersTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users (Id MEDIUMINT NOT NULL AUTO_INCREMENT, Name CHAR(30) NOT NULL, LastName CHAR(30) NOT NULL, Age MEDIUMINT UNSIGNED NOT NULL, PRIMARY KEY (Id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT Users(Name, LastName, Age) VALUES (?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – '" + name + "' добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Users WHERE Id = ?;");
            statement.setByte(1, (byte) id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT Name, LastName, Age FROM Users;");

            while (resultSet.next()) {
                list.add(new User(resultSet.getString("Name"), resultSet.getString("LastName"), resultSet.getByte("Age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;

    }

    @Override
    public void cleanUsersTable() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
