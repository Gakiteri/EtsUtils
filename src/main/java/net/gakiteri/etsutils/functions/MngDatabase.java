package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.PlayerData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static net.gakiteri.etsutils.Variables.playerData;
import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

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

    private Statement makeConnection() {
        try {
            initConnection();
            return connection.createStatement();
        } catch (Exception e) {
            getLogger().warning("Could not connect to database");
        }

        return null;
    }

    /** PLAYER QUERIES **/
    public void updatePlayer(PlayerData player) {



    }
    public PlayerData getPlayer(UUID uuid) throws SQLException {

        PlayerData playerData = new PlayerData();
        playerData.setUuid(uuid);

        ResultSet result = makeConnection().executeQuery("SELECT * FROM Players WHERE UUID = " + uuid.toString() + ";");

        while (result.next()) {
            playerData.setName(result.getString("name"));
            playerData.setOnline(result.getBoolean("online"));
            playerData.setRank(result.getString("rank"));
            playerData.setBalance(result.getInt("balance"));
            playerData.setPvp(result.getBoolean("pvp"));
        }



        return playerData;
    }


}
