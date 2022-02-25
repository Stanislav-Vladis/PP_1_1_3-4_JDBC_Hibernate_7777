package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь

        //1. Создание таблицы User(ов)
        userService.createUsersTable();
        //2. Добавление 4 User(ов) в таблицу с данными на свой выбор.
        userService.saveUser("Коля", "Петров", (byte) 25);
        userService.saveUser("Вася", "Иванов", (byte) 27);
        userService.saveUser("Дима", "Маслеников", (byte) 18);
        userService.saveUser("Саша", "Владимирович", (byte) 20);
        //3. Получение всех User из базы и вывод в консоль
        userService.getAllUsers().stream().forEach(System.out::println);
        //4. Очистка таблицы User(ов)
        userService.cleanUsersTable();
        //5. Удаление таблицы
        userService.dropUsersTable();
        //Закрываем подключение к БД
        UserDaoJDBCImpl.close();
    }

}
