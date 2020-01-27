package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.PlayerData;
import org.apache.commons.lang.CharSet;
import org.bukkit.scheduler.BukkitRunnable;

import java.nio.charset.Charset;
import java.sql.*;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class MngDatabase {

    private Connection connection;
    private Statement statement;


    public BukkitRunnable asyncConnection = new BukkitRunnable() {
        @Override
        public void run() {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mariadb://" + Database.host+ ":" + Database.port + "/" + Database.database + "?user=" + Database.username); // + "&password=" + Database.password);
                statement = connection.createStatement();

                statement.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (" +
                        "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                        "`UUID` tinytext NOT NULL, " +
                        "`username` tinytext NOT NULL, " +
                        "`state` tinytext NOT NULL, " +
                        "`rank` tinytext NOT NULL, " +
                        "`balance` int(11) NOT NULL," +
                        "`pvp` tinyint(1) NOT NULL " +
                        ");");

            } catch (SQLException | ClassNotFoundException e) {
                getLogger().warning("Could not connect to database");
                e.printStackTrace();
            }
        }
    };
/*
    public void initConnection() throws SQLException, ClassNotFoundException {
        //Class.forName("com.mysql.jdbc.Driver");
        Class.forName("org.mariadb.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mariadb://" + Database.host+ ":" + Database.port + "/" + Database.database + "?user=" + Database.username); // + "&password=" + Database.password);
        statement = connection.createStatement();
    }
*/
/*
    public void initDatabase() throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (\n" +
                "  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,\n" +
                "  `uuid` int NOT NULL,\n" +
                "  `username` int NOT NULL,\n" +
                "  `status` int NOT NULL,\n" +
                "  `pvp` int NOT NULL,\n" +
                "  `role` varchar(50) NOT NULL,\n" +
                "  `lastTime` datetime NOT NULL,\n" +
                "  `time_online` int NOT NULL\n" +
                "  `balance` float NOT NULL \n" +
                ") ENGINE='MariaDB';");
    }
*/
/*
    private void makeConnection() {
        try {
            new MngDatabase().initConnection();
        } catch (SQLException | ClassNotFoundException e) {
            getLogger().warning("Could not connect to database");
            e.printStackTrace();
        }
    }
*/
    /** PLAYER QUERIES **/
    public void updatePlayer(PlayerData player) {

        try {

            String online = player.getOnline() ? "on" : "off";
            String pvp = player.getPvp() ? "1" : "0";

            getServer().broadcastMessage("database access 3");
            statement.executeUpdate("UPDATE players SET "
                    + "username = '" + player.getName()
                    + "', state = " + online
                    + ", rank = '" + player.getRank()
                    + "', balance = " + player.getBalance()
                    + ", pvp = " + pvp
                    + " WHERE UUID = " + player.getUuid().toString() +";");

        } catch (Exception e) {
            getLogger().warning("Could not update database");
        }
    }
    public PlayerData getPlayer(UUID uuid) throws SQLException {

        PlayerData playerData = new PlayerData();
        playerData.setUuid(uuid);

        try {
            getServer().broadcastMessage("database access 1");
            ResultSet result = statement.executeQuery("SELECT * FROM players WHERE UUID = " + uuid.toString() + ";");

            getServer().broadcastMessage("post error");

            result.next();
            playerData.setName(result.getString("username"));
            playerData.setOnline(result.getBoolean("state"));
            playerData.setRank(result.getString("rank"));
            playerData.setBalance(result.getInt("balance"));
            playerData.setPvp(result.getBoolean("pvp"));
        } catch (Exception e) {
            e.printStackTrace();

            String name = getServer().getPlayer(uuid).getName();
            getServer().broadcastMessage("database access 2");
            try {
                statement.executeUpdate("INSERT INTO players (UUID, username, state, rank, balance, pvp) VALUES ('" + uuid.toString() + "', '" + name + "', 'off', 'rank', '100', '0');");
            } catch (Exception ex) {
                getLogger().warning("Could not insert to database");
                ex.printStackTrace();
            }

        }

        return playerData;
    }


}
