package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

//Данный класс реализует все функции необходимые для извлечения, обновления и удаления объектов User
public class UserDaoHibernateImpl implements UserDao {
    private static SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {

    }

    static {
        sessionFactory = Util.getSessionFactory();
    }

    public static void close() {
        sessionFactory.close();
    }


    @Override
    public void createUsersTable() {

        //Запрашиваем отдельную сессию.
        Session session = sessionFactory.getCurrentSession();
        //Любая операция с БД должна выполняться в рамках транзакции.
        //Открываем транзакцию
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS Users (Id MEDIUMINT NOT NULL AUTO_INCREMENT, Name CHAR(30) NOT NULL, LastName CHAR(30) NOT NULL, Age MEDIUMINT UNSIGNED NOT NULL, PRIMARY KEY (Id));";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        //Закрываем транзакцию
        transaction.commit();

    }

    @Override
    public void dropUsersTable() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS Users;";
        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        transaction.commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.save(new User(name, lastName, age));

        transaction.commit();
        System.out.println("User с именем – '" + name + "' добавлен в базу данных");

    }

    @Override
    public void removeUserById(long id) {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        //Место U может быть другой символ
        //User - это имя класса с которым работаем!
        String sql = "DELETE FROM User U WHERE U.id = :id";
        Query query = session.createQuery(sql).setParameter("id", id);
        query.executeUpdate();

        transaction.commit();

    }

    @Override
    public List<User> getAllUsers() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        List<User> list = session.createQuery("SELECT U FROM User U", User.class).getResultList();

        transaction.commit();

        return list;
    }

    @Override
    public void cleanUsersTable() {

        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DELETE FROM User";
        Query query = session.createQuery(sql);
        query.executeUpdate();

        transaction.commit();

    }

}
