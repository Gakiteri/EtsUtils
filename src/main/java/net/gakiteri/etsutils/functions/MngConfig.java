package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import static org.bukkit.Bukkit.*;

public class MngConfig {

    private static Plugin plugin = getPluginManager().getPlugin(Variables.pluginName);
    private static FileConfiguration config = Variables.config;

    /** LOAD CONFIG **/
    public static void load() {

        // Defaults
        Variables.defPlayerRank = config.getString("player.rank");
        Variables.defPlayerPvp = config.getBoolean("player.pvp");

        Variables.defCmdPlayer = config.getBoolean("commands.player");
        Variables.defCmdPvp = config.getBoolean("commands.pvp");
        Variables.defCmdRank = config.getBoolean("commands.rank");
        Variables.defCmdGet = config.getBoolean("commands.get");


        // Database
        Database.active = config.getBoolean("database.active");

        if (Database.active) {
            Database.host = config.getString("database.host");
            Database.port = config.getInt("database.port");
            Database.database = config.getString("database.database");
            Database.username = config.getString("database.username");
            Database.password = config.getString("database.password");
        }
    }

    /** LOAD DATABASE DEPENDANT CONFIG **/
    public BukkitRunnable loadWthDb = new BukkitRunnable() {
        @Override
        public void run() {
            config.getList("ranks").forEach(rank -> {
                String rankName = (String) rank;

                DataRank dataRank = new DataRank();
                dataRank.setName(rankName);
                dataRank.setDisplay(rankName);

                if (Database.canConnect) {
                    if (!new MngDatabase().hasRank(rankName)) {
                        new MngDatabase().addRank(dataRank);
                    }
                }
            });
        }
    };

    /** SAVE CONFIG **/
    public static void save() {

        // Database
        config.set("database.active", Database.active);
        config.set("database.host", Database.host);
        config.set("database.port", Database.port);
        config.set("database.database", Database.database);
        config.set("database.username", Database.username);
        config.set("database.password", Database.password);

        // Player
        config.set("player.rank", Variables.defPlayerRank);
        config.set("player.pvp", Variables.defPlayerPvp);

        // Commands
        config.set("commands.player", Variables.defCmdPlayer);
        config.set("commands.pvp", Variables.defCmdPvp);
        config.set("commands.rank", Variables.defCmdRank);
        config.set("commands.get", Variables.defCmdGet);

        // Ranks
        if (Database.canConnect) {
            config.set("ranks", new MngDatabase().getRankList());
        }


        plugin.saveConfig();

        /*
        // Add players playtime
        Variables.playerData.forEach((player, dataset) -> {
            config.set("players.playtime." + player, dataset.getTime());
        });

        // Saves to file
        plugin.saveConfig();
*/
    }
}
