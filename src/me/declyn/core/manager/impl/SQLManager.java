package me.declyn.core.manager.impl;

import me.declyn.core.manager.Manager;
import me.declyn.core.manager.ManagerHandler;
import me.declyn.core.util.CC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLManager extends Manager {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public SQLManager(ManagerHandler managerHandler) {
        super(managerHandler);
        connect();
        if (connection != null) {
            setup();
        }
    }

    private void setup() {
        try {
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS playerdata (NAME varchar(255), UUID varchar(255), IP varchar(255), RANK varchar(255))").executeUpdate();
            connection.prepareStatement("CREATE TABLE IF NOT EXISTS ranks (NAME varchar(255), PREFIX varchar(255), COLOR varchar(255), WEIGHT varchar(255), `DEFAULT` varchar(255))").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void connect() {
        host = managerHandler.getFileManager().getOptionsYaml().getString("database.sql.host");
        port = managerHandler.getFileManager().getOptionsYaml().getInt("database.sql.port");
        database = managerHandler.getFileManager().getOptionsYaml().getString("database.sql.database");
        username = managerHandler.getFileManager().getOptionsYaml().getString("database.sql.username");
        password = managerHandler.getFileManager().getOptionsYaml().getString("database.sql.password");

        try {
            synchronized (this) {

                if (connection != null) {
                    return;
                }

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ':' + this.port + '/' + this.database, this.username, this.password);
                sendConsoleMessage(CC.GREEN + "[Core] Connected to the SQL database.");
            }
        } catch (SQLException e) {
            sendConsoleMessage(CC.RED + "[Core] Could not connect to the SQL database.");
        } catch (ClassNotFoundException e) {
            sendConsoleMessage(CC.RED + "[Core] Could not find SQL driver class.");
        }
    }

    public Connection getConnection() {
        return connection;
    }

}
