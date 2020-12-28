package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    Session session = Util.getSessionFactory().openSession();

    @Override
    public void createUsersTable() {
        Transaction transaction = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS USERS\n" +
                "(\n" +
                "\tID int NOT NULL  AUTO_INCREMENT,\n" +
                "\tNAME varchar(255) not null,\n" +
                "\tLASTNAME varchar(255) not null,\n" +
                "\tAGE TINYINT not null,\n" +
                "\tconstraint USERS_pk\n" +
                "\t\tprimary key (ID)\n" +
                ");";
        try {
            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка создании таблицы.");
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS USERS").executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка удаления таблицы.");
            throw new IllegalArgumentException();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Transaction transaction = this.session.beginTransaction();
            User user = new User(name, lastName, age);
            if (user != null)
                session.save(user);
            transaction.commit();
            System.out.println("Пользователь: " + name + " " + lastName + " " + age + " лет добавлен в таблицу.");
        } catch (HibernateException e) {
            System.out.println("Ошибка добавления пользователя: " + name + " " + lastName + " " + age);
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void removeUserById(long id) {

        try {
            Transaction transaction = this.session.beginTransaction();
            Object user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
            System.out.println("Удаление пользователя по id = " + id);
        } catch (HibernateException e) {
            System.out.println("Ошибка удаления пользователя.");
            e.printStackTrace();
            throw new IllegalArgumentException();
        }


    }

    @Override
    public List<User> getAllUsers() {

        CriteriaBuilder builder = this.session.getCriteriaBuilder();
        Class<User> user = User.class;
        CriteriaQuery<User> query = builder.createQuery(user);
        Root<User> root = query.from(user);
        query.select(root);
        Query<User> q = this.session.createQuery(query);
        List<User> list = q.getResultList();


        return list;

    }

    @Override
    public void cleanUsersTable() {

        try {
            Transaction transaction = this.session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE USERS").executeUpdate();
            transaction.commit();
            System.out.println("Таблица очищена.");
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println("Ошибка очистки таблицы.");
            throw new IllegalArgumentException();
        }
    }
}
