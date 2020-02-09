package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatMessage implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {

        String msg = event.getMessage();
        msg = msg.replace("&&", "§");
        String display = "";

        if (Database.canConnect) {
            DataPlayer dataPlayer = new MngDatabase().getPlayer(event.getPlayer().getUniqueId());
            display = new MngDatabase().getRank(dataPlayer).getDisplay();
        }

        event.setFormat(display + ChatColor.GRAY + " %1$s:" + ChatColor.RESET + " %2$s");
        event.setMessage(msg);
    }
}
