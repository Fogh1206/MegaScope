package server.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ControllerDAO {
    private static ControllerDAO instance;

    private ControllerDAO() {
    }

    /**
     *Static method that returns an instance
     */
    public static ControllerDAO getInstance() {
        if (instance == null) {
            instance = new ControllerDAO();
        }
        return instance;
    }

    /**
     * Method for making the database connection
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://tai.db.elephantsql.com:5432/seitjdhj";
        String username = "seitjdhj";
        String password = "9LEmAjua_Uo0YR5sGqAFHn0Kgm9DDKu1";
        return DriverManager.getConnection(url, username, password);
    }
}
