package jm.task.core.jdbc.util;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/task_maven";
    private static final String usserName = "root";
    private static final String pass = "BxrgAZfhg2ZJXQE";

    public static Connection getConnection() {
        Connection connection  = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
              connection = (Connection) DriverManager.getConnection(url, usserName, pass);
            System.out.println("Connection Successful");
            } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Connection Error");

            }

        return connection;
    }
}
