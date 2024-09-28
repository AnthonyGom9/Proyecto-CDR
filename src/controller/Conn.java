package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author KebFelipe
 */
public class Conn {
    private final String URL = "jdbc:mysql://localhost:3306/CDR";
    private final String USER = "root";
    private final String PASSWORD = "R00tP4ssw0rd"; 

    public Conn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: " + e);
        }
    }

    public Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
