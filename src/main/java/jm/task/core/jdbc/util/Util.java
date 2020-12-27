package jm.task.core.jdbc.util;

import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/task_maven";
    private static final String usserName = "root";
    private static final String pass = "BxrgAZfhg2ZJXQE";


    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(url, usserName, pass);
            System.out.println("Соединение установлено.");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Ошибка соединения.");

        }

        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties properties = new Properties();
                properties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.setProperty(Environment.HBM2DDL_AUTO, "update");
                properties.setProperty(Environment.DRIVER, "com.mysql.jdbc.Driver");
                properties.setProperty(Environment.USER, usserName);
                properties.setProperty(Environment.PASS, pass);
                properties.setProperty(Environment.URL, url);

                configuration.setProperties(properties);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}




