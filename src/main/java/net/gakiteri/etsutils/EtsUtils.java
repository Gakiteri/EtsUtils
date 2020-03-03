package net.gakiteri.etsutils;

import net.gakiteri.etsutils.commands.*;
import net.gakiteri.etsutils.events.*;
import net.gakiteri.etsutils.functions.*;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class EtsUtils extends JavaPlugin {

    @Override
    public void onEnable() {
        Server server = getServer();
        PluginManager pluginManager = server.getPluginManager();
        Variables.pluginName = this.getName();

        /** GET PLUGIN DIRECTORY **/
        Variables.dirPlugin = MngFile.path(getDataFolder());

        /** GET CONFIG **/
        getConfig().options().copyDefaults();
        //saveDefaultConfig(); ------------------------------- Check this line

        Variables.config = getConfig();
        MngConfig.load();

        /** COMMAND REGISTRATION **/
        if (Variables.defCmdBalance) { this.getCommand("balance").setExecutor(new CmdBalance()); }
        if (Variables.defCmdPlayer) { this.getCommand("player").setExecutor(new CmdPlayer()); }
        if (Variables.defCmdPvp) { this.getCommand("pvp").setExecutor(new CmdPvp()); }
        if (Variables.defCmdRank) { this.getCommand("rank").setExecutor(new CmdRank()); }


        /** EVENT REGISTRATION **/
        pluginManager.registerEvents(new OnChatMessage(), this);
        pluginManager.registerEvents(new OnJoin(), this);
        pluginManager.registerEvents(new OnLeave(), this);
        pluginManager.registerEvents(new OnPvp(), this);

        /** TASK REGISTRATION **/
        //BukkitTask GetExample = new GetExample(this).runTaskTimer(this, 0L, 40L);
        BukkitTask connectDatabase = new MngDatabase().asyncConnection.runTaskLaterAsynchronously(this, 1L);
        BukkitTask loadDbDependant = new MngConfig().loadWthDb.runTaskLater(this, 10L);

        // Plugin startup message
        getLogger().info(Variables.pluginName + " plugin initialised");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
