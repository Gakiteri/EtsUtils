package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getPluginManager;

public class MngConfig {

    private static Plugin plugin = getPluginManager().getPlugin(Variables.pluginName);
    private static FileConfiguration config = Variables.config;

    /** LOAD DATABASE CONFIG **/
    public static void loadDb() {

        Database.active = config.getBoolean("database.active");

        if (Database.active) {
            Database.host = config.getString("database.host");
            Database.port = config.getInt("database.port");
            Database.database = config.getString("database.database");
            Database.username = config.getString("database.username");
            Database.password = config.getString("database.password");

            new MngDatabase().asyncConnection.runTaskAsynchronously(plugin);
        }
    }

    public static void loadNoDb() {

        Variables.defCmdBalance = config.getBoolean("default-config.commands.balance");
        Variables.defCmdPlayer = config.getBoolean("default-config.commands.player");
        Variables.defCmdPvp = config.getBoolean("default-config.commands.pvp");
        Variables.defCmdRank = config.getBoolean("default-config.commands.rank");

    }
    public static void loadWthDb() {

        config.getMapList("default-config.ranks").forEach(set -> {
            set.forEach((i, o) -> {
                int level = Integer.parseInt(i.toString());
                String name = o.toString();

                DataRank dataRank = new DataRank();
                dataRank.setLevel(level);
                dataRank.setName(name);
                dataRank.setDisplay(name);

                if (Database.canConnect) {
                    if (!new MngDatabase().hasRank(name)) {
                        new MngDatabase().addRank(dataRank);
                    }
                }
            });
        });

        Variables.defPlayerRank = config.getString("default-config.player.rank");
        if (Database.canConnect) {
            Variables.defPlayerRankLevel = new MngDatabase().getRank(Variables.defPlayerRank).getLevel();
        }
    }


    public void save() {
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
