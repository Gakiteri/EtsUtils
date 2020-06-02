package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.Variables;
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

        // If keywords are detected, activates message at night
        String msgLower = msg.toLowerCase();
        if (msgLower.contains("no ") && (msgLower.contains("dorm") || msgLower.contains("durm") || msgLower.contains("sleep"))) {
            Variables.dtaNoSleep = true;
            event.getPlayer().sendMessage(ChatColor.GOLD + "COMANDO ACTIVADO");
        }

        if (Database.canConnect) {
            DataPlayer dataPlayer = new MngDatabase().getPlayer(event.getPlayer().getUniqueId());
            display = new MngDatabase().getRank(dataPlayer).getDisplay();
        }

        event.setFormat(display + ChatColor.RESET + " %1$s: %2$s");
        event.setMessage(msg);
    }
}
