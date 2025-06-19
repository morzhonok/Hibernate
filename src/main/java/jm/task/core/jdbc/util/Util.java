package jm.task.core.jdbc.util;
import com.sun.istack.logging.Logger;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class Util {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";
    public static final String URL = "jdbc:mysql://localhost:3306/mydbtest";
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class.getName().getClass());
    static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            logger.log(Level.INFO, "Не удалось загрузить класс драйвера" + e.getMessage(), e);
        }
        return connection;
    }
}