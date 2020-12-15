package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.createUsersTable();
        userService.saveUser("Vasili", "Petrov", (byte) 15);
        userService.saveUser("Victor", "Ivanov", (byte) 60);
        userService.saveUser("Macha", "Machina", (byte) 49);
        userService.saveUser("Carl", "Gallager", (byte) 20);
        System.out.println(userService.getAllUsers());
        userService.removeUserById(3);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
