package net.gakiteri.etsutils;

import net.gakiteri.etsutils.functions.MngConfig;
import net.gakiteri.etsutils.functions.MngFile;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class EtsUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();
        Variables.pluginName = this.getName();

        /** COMMAND REGISTRATION **/
        //this.getCommand("pvp").setExecutor(new CmdPvp());


        /** EVENT REGISTRATION **/
        //pluginManager.registerEvents(new OnJoin(), this);


        /** GET PLUGIN DIRECTORY **/
        Variables.dirPlugin = MngFile.path(getDataFolder());

        /** GET CONFIG **/
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Variables.config = getConfig();
        new MngConfig().load();


        // Plugin startup message
        getLogger().info(" plugin initialised");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
