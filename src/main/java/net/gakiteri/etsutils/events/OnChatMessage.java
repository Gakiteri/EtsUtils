package net.gakiteri.etsutils.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatMessage implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {

        String msg = event.getMessage();
        Player player = event.getPlayer();

        msg = msg.replace("&&", "§");

        event.setMessage(msg);
    }
}
