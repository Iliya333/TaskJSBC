package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    Connection connection = Util.getConnection();


    public void createUsersTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS USERS\n" +
                "(\n" +
                "\tID int NOT NULL  AUTO_INCREMENT,\n" +
                "\tNAME varchar(255) not null,\n" +
                "\tLASTNAME varchar(255) not null,\n" +
                "\tAGE TINYINT not null,\n" +
                "\tconstraint USERS_pk\n" +
                "\t\tprimary key (ID)\n" +
                ");";

        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        System.out.println("Создана новая таблица.");

    }


    public void dropUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DROP TABLE IF EXISTS USERS";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Удаление таблицы");

    }


    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "INSERT INTO users( NAME, LASTNAME, AGE) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);
            System.out.println("Пользователь " + name + " " + lastName + " " + age + " Добавлен в базу данных");
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();


        }

    }

    public void removeUserById(long id) throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "DELETE  FROM USERS WHERE ID=?";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Удаление User из таблицы ( по id = " + id + " )");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<User> getAllUsers() throws SQLException {
        List<User> usersList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM USERS";

        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(Byte.valueOf(resultSet.getString("AGE")));

                usersList.add(user);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return usersList;
    }

    public void cleanUsersTable() throws SQLException {
        PreparedStatement preparedStatement = null;

        String sql = "TRUNCATE TABLE USERS";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            System.out.println("Очистка таблицы User(ов)");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

