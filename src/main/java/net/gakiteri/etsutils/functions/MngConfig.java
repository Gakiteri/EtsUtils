package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getPluginManager;

public class MngConfig {

    private Plugin plugin = getPluginManager().getPlugin(Variables.pluginName);
    private FileConfiguration config = Variables.config;

    /** LOAD EVERYTHING **/
    public void load() {

        loadDb();

        // Load players playtime
        config.getMapList("players.playtime").forEach(set -> {
            set.forEach((player, time) -> {
                Variables.playerData.put((String) player, new DataPlayer());
            });
        });

        config.getMapList("database.config").forEach(set -> {
            set.forEach((key, value) -> {
                Variables.configSql.put((String) key, value);
            });
        });

    }

    /** LOAD DATABASE CONFIG **/
    public void loadDb() {

        Database.active = config.getBoolean("database.active");

        if (Database.active) {
            Database.host = config.getString("database.host");
            Database.port = config.getInt("database.port");
            Database.database = config.getString("database.database");
            Database.username = config.getString("database.username");
            Database.password = config.getString("database.password");
        } else {
            Database.setNull();
        }

    }


    public void save() {

        // Add players playtime
        Variables.playerData.forEach((player, dataset) -> {
            config.set("players.playtime." + player, dataset.getTime());
        });

        // Saves to file
        plugin.saveConfig();

    }


}
