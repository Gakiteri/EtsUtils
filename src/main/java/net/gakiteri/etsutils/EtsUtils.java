package net.gakiteri.etsutils;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.events.OnJoin;
import net.gakiteri.etsutils.functions.MngConfig;
import net.gakiteri.etsutils.functions.MngDatabase;
import net.gakiteri.etsutils.functions.MngFile;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.SQLException;

import static org.bukkit.Bukkit.getLogger;

public final class EtsUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();
        Variables.pluginName = this.getName();

        /** COMMAND REGISTRATION **/
        //this.getCommand("pvp").setExecutor(new CmdPvp());


        /** EVENT REGISTRATION **/
        pluginManager.registerEvents(new OnJoin(), this);

        /** TASK REGISTRATION **/
        //BukkitTask GetTime = new GetTime(this).runTaskTimer(this, 0L, 40L);

        /** GET PLUGIN DIRECTORY **/
        Variables.dirPlugin = MngFile.path(getDataFolder());

        /** GET CONFIG **/
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Variables.config = getConfig();
        new MngConfig().load();

        /** GET DATABASE **/
        if (Database.active) {
            new MngDatabase().asyncConnection.runTaskAsynchronously(this);
            if (Database.canConnect) {
                new MngDatabase().initTables();
            }
        }





        // Plugin startup message
        getLogger().info(Variables.pluginName + " plugin initialised");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
