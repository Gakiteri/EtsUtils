package net.gakiteri.etsutils;

import net.gakiteri.etsutils.commands.*;
import net.gakiteri.etsutils.events.*;
import net.gakiteri.etsutils.functions.*;
import net.gakiteri.etsutils.tasks.LogPlayers;
import net.gakiteri.etsutils.tasks.Timer2s;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EtsUtils extends JavaPlugin {

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
        if (Variables.defCmdPlayer) { this.getCommand("player").setExecutor(new CmdPlayer()); }
        if (Variables.defCmdPvp) { this.getCommand("pvp").setExecutor(new CmdPvp()); }
        if (Variables.defCmdRank) { this.getCommand("rank").setExecutor(new CmdRank()); }
        if (Variables.defCmdGet) { this.getCommand("get").setExecutor(new CmdGet()); }


        /** EVENT REGISTRATION **/
        pluginManager.registerEvents(new OnChatMessage(), this);
        pluginManager.registerEvents(new OnJoin(), this);
        pluginManager.registerEvents(new OnLeave(), this);
        pluginManager.registerEvents(new OnPvp(), this);
        pluginManager.registerEvents(new OnSleep(), this);

        /** TASK REGISTRATION **/
        BukkitTask connectDatabase = new MngDatabase().asyncConnection.runTaskLaterAsynchronously(this, 1L);
        BukkitTask loadDbDependant = new MngConfig().loadWthDb.runTaskLater(this, 10L);
        BukkitTask getTime = new Timer2s(this).runTaskTimerAsynchronously(this, 0L, 40L);
        BukkitTask logPlayers = new LogPlayers(this).runTaskTimerAsynchronously(this, 0L, 600L);


        /** PERMISSIONS **/

        // Plugin startup message
        getLogger().info(Variables.pluginName + " plugin initialised");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
