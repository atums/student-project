package com.apys.learning.dao;

import com.apys.learning.config.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBuilder {
    public static Connection getConnection () throws SQLException {
        Connection con = DriverManager.getConnection(
                Config.getProperties(Config.DB_URL),
                Config.getProperties(Config.DB_LOGIN),
                Config.getProperties(Config.DB_PASSWORD));

        return con;
    }
}
