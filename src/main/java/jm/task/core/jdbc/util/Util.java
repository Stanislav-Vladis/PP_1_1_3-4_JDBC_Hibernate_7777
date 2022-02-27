package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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
    //Настройка и работа с сессиями
    private  static SessionFactory sessionFactory;

    static {

        try {
            //Задаем конфигурацию
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/kata_preproject_bd?useSSL=false&amp;serverTimezone=UTC")
                    .setProperty("hibernate.connection.username", "root IDEA")
                    .setProperty("hibernate.connection.password", "password")
                    .setProperty("hibernate.show_sql", "false")
                    .setProperty("hibernate.format_sql", "true")
                    .setProperty("hibernate.current_session_context_class", "thread")
                    .addAnnotatedClass(User.class);

            sessionFactory = configuration.configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*
        //Получаем реестр сервисов, настраиваем конфигурацию из hibernate.cfg.xml и билдим.
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            //MetadataSources - нужен для работы с метаданными маппинга объектов
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            //При возникновении ощибок разрущаем реестр
            StandardServiceRegistryBuilder.destroy(registry);
        }
        */

    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }




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
