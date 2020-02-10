package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class MngConfig {

    private static Plugin plugin = getPluginManager().getPlugin(Variables.pluginName);
    private static FileConfiguration config = Variables.config;

    /** LOAD CONFIG **/
    public static void load() {

        // Defaults
        Variables.defPlayerRank = config.getString("default-config.player.rank");

        Variables.defCmdBalance = config.getBoolean("default-config.commands.balance");
        Variables.defCmdPlayer = config.getBoolean("default-config.commands.player");
        Variables.defCmdPvp = config.getBoolean("default-config.commands.pvp");
        Variables.defCmdRank = config.getBoolean("default-config.commands.rank");


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
            config.getMapList("default-config.ranks").forEach(set -> {
                set.forEach((i, o) -> {
                    ((ArrayList<String>) o).forEach(name -> {

                        int level = Integer.parseInt(i.toString());

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
            });

            if (Database.canConnect) {
                Variables.defPlayerRankLevel = new MngDatabase().getRank(Variables.defPlayerRank).getLevel();
            }
        }
    };

    /** SAVE CONFIG **/
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
