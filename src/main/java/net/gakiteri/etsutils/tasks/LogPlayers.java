package net.gakiteri.etsutils.tasks;

import net.gakiteri.etsutils.EtsUtils;
import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataLocationLog;
import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.UUID;
import static org.bukkit.Bukkit.getServer;

public class LogPlayers extends BukkitRunnable {

    EtsUtils plugin;

    public LogPlayers(EtsUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        if (Database.canConnect) {
            getServer().getOnlinePlayers().forEach(player -> {
                UUID uuid = player.getUniqueId();
                String strUuid = uuid.toString();

                // Add locationLog data to common variables list
                if (!Variables.dtaLocationLogs.containsKey(strUuid)) {
                    Variables.dtaLocationLogs.put(uuid.toString(), new DataLocationLog(player));
                }

                DataLocationLog locationLog = Variables.dtaLocationLogs.get(strUuid);

                // Update players log
                locationLog.updateData(player);

                // Update player state on DB
                if (!locationLog.getAfkUpdated()) {
                    DataPlayer dataPlayer = new MngDatabase().getPlayer(uuid);
                    dataPlayer.setState(locationLog.getAfk() ? "afk" : "on");
                    new MngDatabase().updatePlayer(dataPlayer);
                    locationLog.setAfkUpdated(true);
                }

                // If not afk, add new log
                if (!locationLog.getAfk()) {
                    new MngDatabase().addLocationLog(locationLog);
                }

                // Replace log variable of player
                Variables.dtaLocationLogs.replace(strUuid, locationLog);

            });
        }

    }
}
