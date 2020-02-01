package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.DataPlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.*;
import java.util.ArrayList;
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
                initTables();
            } catch (Exception e) {
                onError(e);
            }
        }
    };

    /** INITIALISE TABLES **/
    private void initTables() {
        try {
            // players
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (" +
                    "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "`UUID` tinytext NOT NULL, " +
                    "`username` tinytext NOT NULL, " +
                    "`state` tinytext NOT NULL, " +
                    "`rank` tinytext NOT NULL, " +
                    "`balance` int(11) NOT NULL," +
                    "`pvp` tinyint(1) NOT NULL " +
                    ");");

            // ranks
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ranks` (" +
                    "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "`level` int(11) NOT NULL, " +
                    "`name` tinytext NOT NULL," +
                    "`display` tinytext NOT NULL" +
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
    public DataPlayer getPlayer(UUID uuid) {
        DataPlayer dataPlayer = new DataPlayer();
        dataPlayer.setUuid(uuid);

        try {
            ResultSet result = statement.executeQuery("SELECT * FROM players WHERE UUID = '" + uuid.toString() + "';");

            if (result.next()) {
                dataPlayer.setName(result.getString("username"));
                dataPlayer.setState(result.getString("state"));
                dataPlayer.setRank(result.getString("rank"));
                dataPlayer.setBalance(result.getInt("balance"));
                dataPlayer.setPvp(result.getBoolean("pvp"));
            } else {
                String name = getServer().getPlayer(uuid).getName();
                statement.executeUpdate("INSERT INTO players " +
                        "(UUID, username, state, rank, balance, pvp) VALUES"
                        + " ('" + uuid.toString()
                        + "','" + name
                        + "','" + dataPlayer.getState()
                        + "','" + dataPlayer.getRank()
                        + "','" + dataPlayer.getBalance()
                        + "','" + (dataPlayer.getPvp() ? "1" : "0")
                        + "');");
            }

        } catch (Exception e) {
            onError(e);
        }

        return dataPlayer;
    }

    public void updatePlayer(DataPlayer player) {
        try {
            String query = "UPDATE players SET "
                    + "username = '" + player.getName()
                    + "', state = '" + player.getState()
                    + "', rank = '" + player.getRank()
                    + "', balance = '" + player.getBalance()
                    + "', pvp = '" + (player.getPvp() ? "1" : "0")
                    + "' WHERE UUID = '" + player.getUuid().toString()
                    + "';";
            statement.executeUpdate(query);
        } catch (Exception e) {
            onError(e);
        }
    }


    /** RANK QUERIES **/
    public ArrayList<DataRank> getRankList() {

        ArrayList<DataRank> ranks = new ArrayList<>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM ranks WHERE 1;");
            while (result.next()) {
                DataRank rank = new DataRank();
                rank.setLevel(result.getInt("level"));
                rank.setName(result.getString("name"));
                rank.setDisplay(result.getString("display"));
                ranks.add(rank);
            }
        } catch (Exception e) {
            onError(e);
        }

        return ranks;
    }

    public boolean addRank(DataRank rank) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranks WHERE name = '" + rank.getName() + "';");
            if (!resultSet.next()) {
                statement.executeUpdate("INSERT INTO ranks "
                        + "(level, name, display) VALUES"
                        + " ('" + rank.getLevel()
                        + "','" + rank.getName()
                        + "','" + rank.getDisplay()
                        + "');");
                return true;
            }
        } catch (Exception e) {
            onError(e);
        }
        return false;
    }

    public boolean removeRank(String rankName) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranks WHERE name = '" + rankName + "';");
            if (resultSet.next()) {
                statement.executeUpdate("DELETE FROM ranks WHERE name = '" + rankName + "';");
                return true;
            }
        } catch (Exception e) {
            onError(e);
        }

        return false;
    }
    /*
    statement.executeUpdate("INSERT INTO ranks " +
            "(level, name, display) VALUES"
            + " ('" + rank.getLevel()
            + "','" + rank.getName()
            + "','" + rank.getDisplay()
            + ");");

     */
}
