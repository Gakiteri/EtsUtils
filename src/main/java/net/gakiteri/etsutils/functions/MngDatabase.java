package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataLocationLog;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.DataPlayer;
import org.bukkit.scheduler.BukkitRunnable;
import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;
import static org.bukkit.Bukkit.getLogger;

public class MngDatabase {

    private static Statement statement;

    /** CONNECTS TO DATABASE **/
    public BukkitRunnable asyncConnection = new BukkitRunnable() {
        @Override
        public void run() {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mariadb://" + Database.host+ ":" + Database.port + "/" + Database.database + "?user=" + Database.username); // + "&password=" + Database.password);
                statement = connection.createStatement();
                getLogger().info(Variables.pluginName + " Database connection established");
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
            // ranks
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `ranks` (" +
                    "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "`rankname` varchar(15) NOT NULL UNIQUE, " +
                    "`display` tinytext NOT NULL " +
                    ");");

            // players
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `players` (" +
                    "`ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "`UUID` varchar(40) NOT NULL UNIQUE, " +
                    "`username` tinytext NOT NULL, " +
                    "`state` tinytext NOT NULL, " +
                    "`rank` varchar(15), " +
                    "CONSTRAINT rankName " +
                    "FOREIGN KEY (rank) " +
                    "REFERENCES ranks(rankname) " +
                    "ON DELETE SET NULL " +
                    "ON UPDATE CASCADE, " +
                    "`pvp` tinyint(1) NOT NULL " +
                    ");");

            // locationLogs
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `locationLogs` (" +
                    "`date` date NOT NULL, " +
                    "`time` time NOT NULL, " +
                    "`playerUUID` varchar(40), " +
                    "`dimension` tinytext NOT NULL, " +
                    "`cordX` tinyint NOT NULL, " +
                    "`cordY` tinyint NOT NULL, " +
                    "`cordZ` tinyint NOT NULL, " +
                    "CONSTRAINT `UUID` FOREIGN KEY (`playerUUID`) REFERENCES players(`UUID`) " +
                    "ON UPDATE CASCADE, " +
                    "CONSTRAINT tablePK PRIMARY KEY (`date`, `time`)" +
                    ");");

        } catch (Exception e) {
            onError(e);
        }
    }

    /** ERROR ON DB CONNECTION **/
    private void onError(Exception e) {
        Database.canConnect = false;
        getLogger().warning("Could not connect/interact to/with database successfully");
        e.printStackTrace();
    }

    /** PLAYER QUERIES **/
    public void addPlayer(DataPlayer dataPlayer) {
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM players WHERE UUID = '" + dataPlayer.getUuid() + "';");
            if (!result.next()) {
                statement.executeUpdate("INSERT INTO players " +
                        "(UUID, username, state, rank, pvp) VALUES"
                        + " ('" + dataPlayer.getUuid()
                        + "','" + dataPlayer.getName()
                        + "','" + dataPlayer.getState()
                        + "','" + dataPlayer.getRank().getName()
                        + "','" + (dataPlayer.getPvp() ? "1" : "0")
                        + "');");
            }
        } catch (Exception e) {
            onError(e);
        }
    }

    public DataPlayer getPlayer(UUID uuid) {
        return getPlayerFromDataBase("SELECT * FROM players WHERE UUID = '" + uuid.toString() + "';");
    }
    public DataPlayer getPlayer(String name) {
        return getPlayerFromDataBase("SELECT * FROM players WHERE username = '" + name + "';");
    }

    private DataPlayer getPlayerFromDataBase(String query) {
        DataPlayer dataPlayer = new DataPlayer();
        try {
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                dataPlayer.setUuid(UUID.fromString(result.getString("UUID")));
                dataPlayer.setName(result.getString("username"));
                dataPlayer.setState(result.getString("state"));
                if (hasRank(result.getString("rank"))) {
                    dataPlayer.setRank(getRank(result.getString("rank")));
                } else {
                    dataPlayer.setRank(new MngDatabase().getRank(Variables.defPlayerRank));
                }
                dataPlayer.setPvp(result.getBoolean("pvp"));
            }
        } catch (Exception e) {
            onError(e);
        }
        return dataPlayer;
    }

    public boolean hasPlayer(UUID uuid) {
        return (getPlayerFromDataBase("SELECT * FROM players WHERE UUID = '" + uuid.toString() + "';").getUuid() != null);
    }
    public boolean hasPlayer(String playerName) {
        return (getPlayerFromDataBase("SELECT * FROM players WHERE username = '" + playerName + "';").getUuid() != null);
    }

    public void updatePlayer(DataPlayer player) {
        try {
            String query = "UPDATE players SET "
                    + "username = '" + player.getName()
                    + "', state = '" + player.getState()
                    + "', rank = '" + player.getRank().getName()
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
                DataRank dataRank = new DataRank();
                dataRank.setName(result.getString("rankname"));
                dataRank.setDisplay(result.getString("display"));
                ranks.add(dataRank);
            }
        } catch (Exception e) {
            onError(e);
        }
        return ranks;
    }

    public boolean addRank(DataRank rank) {
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranks WHERE rankname = '" + rank.getName() + "';");
            if (!resultSet.next()) {
                statement.executeUpdate("INSERT INTO ranks "
                        + "(rankname, display) VALUES"
                        + " ('" + rank.getName()
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
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ranks WHERE rankname = '" + rankName + "';");
            if (resultSet.next()) {
                statement.executeUpdate("DELETE FROM ranks WHERE rankname = '" + rankName + "';");
                return true;
            }
        } catch (Exception e) {
            onError(e);
        }
        return false;
    }

    public DataRank getRank(String rankName) {
        return getRankFromDataBase("SELECT * FROM ranks WHERE rankname = '" + rankName + "';");
    }
    public DataRank getRank(DataPlayer dataPlayer) {
        return getRankFromDataBase("SELECT * FROM ranks WHERE rankname = '" + dataPlayer.getRank().getName() + "';");
    }

    private DataRank getRankFromDataBase(String query) {
        DataRank dataRank = new DataRank();
        try {
            ResultSet result = statement.executeQuery(query);
            if (result.next()) {
                dataRank.setName(result.getString("rankname"));
                dataRank.setDisplay(result.getString("display"));
            }
        } catch (Exception e) {
            onError(e);
        }
        return dataRank;
    }

    public boolean hasRank(String rankName) {
        return (!getRankFromDataBase("SELECT * FROM ranks WHERE rankname = '" + rankName + "';").getName().isEmpty());
    }
    public boolean hasRank(DataPlayer dataPlayer) {
        return (!getRankFromDataBase("SELECT * FROM ranks WHERE rankname = '" + dataPlayer.getRank().getName() + "';").getName().isEmpty());
    }

    public boolean updateRank(DataRank rank) {
        try {
            String query = "UPDATE ranks SET "
                    + "rankname = '" + rank.getName()
                    + "', display = '" + rank.getDisplay()
                    + "' WHERE rankname = '" + rank.getName()
                    + "';";
            statement.executeUpdate(query);
            return true;
        } catch (Exception e) {
            onError(e);
        }
        return false;
    }


    /** LOCATION QUERIES **/
    public void addLocationLog(DataLocationLog log) {
        try {
            statement.executeUpdate("INSERT INTO locationLogs "
                    + "(date, time, playerUUID, dimension, cordX, cordY, cordZ) VALUES"
                    + " ('" + log.getDate()
                    + "','" + log.getTime()
                    + "','" + log.getUuid()
                    + "','" + log.getDimension()
                    + "','" + log.getX()
                    + "','" + log.getY()
                    + "','" + log.getZ()
                    + "');");
        } catch (Exception e) {
            onError(e);
        }

    }



}
