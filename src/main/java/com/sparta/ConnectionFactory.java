package com.sparta;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConnectionFactory {
    private static Connection connection = null;

    public static Connection getConnection() throws IOException, SQLException {
        if (connection == null|| !connection.isValid(1)) {
            Properties props = new Properties();
            props.load(new FileReader("src/main/resources/dbconnect.properties"));
            connection = DriverManager.getConnection(
                    props.getProperty("dburl"),
                    props.getProperty("dbuser"),
                    props.getProperty("dbpassword")
            );
        }
        return connection;
    }

    public static void closeConnection() throws SQLException, InterruptedException {
        if (connection != null) {
            connection.close();
        }
    }
    private ConnectionFactory() {

    }
}
