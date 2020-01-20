package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.data.Database;

import java.sql.*;

import static org.bukkit.Bukkit.getLogger;

public class MngDatabase {

    private Connection connection;

    private void initConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) {
            return;
        }

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + Database.host+ ":" + Database.port + "/" + Database.database, Database.username, Database.password);
        }
    }

    private void makeConnection() {
        try {
            initConnection();
            Statement statement = connection.createStatement();
        } catch (Exception e) {
            getLogger().warning("Could not connect to database");
        }



    }




}
