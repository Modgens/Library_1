package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String dbUrl = "jdbc:mysql://localhost:3306/dbLibrary?useSSL=false";
    private static final String dbName = "root";
    private static final String dbPassword = "32422";
    private static final String dbDriver = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(dbDriver);
        return DriverManager.getConnection(dbUrl, dbName, dbPassword);
    }
}
