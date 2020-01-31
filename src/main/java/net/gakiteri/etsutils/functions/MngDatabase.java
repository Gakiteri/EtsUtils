package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.PlayerData;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.UUID;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class MngDatabase {

    private static Connection connection;
    private static Statement statement;

    /** CONNECTS TO DATABASE **/
    public BukkitRunnable asyncConnection = new BukkitRunnable() {
        @Override
        public void run() {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mariadb://" + Database.host+ ":" + Database.port + "/" + Database.database + "?user=" + Database.username); // + "&password=" + Database.password);
                statement = connection.createStatement();
                Database.canConnect = true;
            } catch (Exception e) {
                onError(e);
            }
        }
    };

    /** INITIALISE TABLES **/
    public void initTables() {
        try {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (" +
                    "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "`UUID` tinytext NOT NULL, " +
                    "`username` tinytext NOT NULL, " +
                    "`state` tinytext NOT NULL, " +
                    "`rank` tinytext NOT NULL, " +
                    "`balance` int(11) NOT NULL," +
                    "`pvp` tinyint(1) NOT NULL " +
                    ");");
        } catch (Exception e) {
            onError(e);
        }
    }

    /** ERROR ON DB CONNECTION **/
    private void onError(Exception e) {
        Database.canConnect = false;
        getLogger().warning("Could not connect/interact to database successfully");
        e.printStackTrace();
    }

    /** PLAYER QUERIES **/
    public PlayerData getPlayer(UUID uuid) {

        PlayerData playerData = new PlayerData();
        playerData.setUuid(uuid);

        try {
            ResultSet result = statement.executeQuery("SELECT * FROM players WHERE UUID = '" + uuid.toString() + "';");

            if (result.next()) {
                playerData.setName(result.getString("username"));
                playerData.setState(result.getString("state"));
                playerData.setRank(result.getString("rank"));
                playerData.setBalance(result.getInt("balance"));
                playerData.setPvp(result.getBoolean("pvp"));
            } else {
                String name = getServer().getPlayer(uuid).getName();
                statement.executeUpdate("INSERT INTO players " +
                        "(UUID, username, state, rank, balance, pvp) VALUES"
                        + " ('" + uuid.toString()
                        + "','" + name
                        + "','" + playerData.getState()
                        + "','" + playerData.getRank()
                        + "','" + playerData.getBalance()
                        + "','" + playerData.getPvp()
                        + ");");
            }

        } catch (Exception e) {
            onError(e);
        }

        return playerData;
    }


    public void updatePlayer(PlayerData player) {

        String pvp = player.getPvp() ? "1" : "0";

        try {
            statement.executeUpdate("UPDATE players SET "
                    + "username = '" + player.getName()
                    + "', state = '" + player.getState()
                    + "', rank = '" + player.getRank()
                    + "', balance = " + player.getBalance()
                    + ", pvp = " + pvp
                    + " WHERE UUID = '" + player.getUuid().toString()
                    + "';");

        } catch (Exception e) {
            onError(e);
        }
    }


}
