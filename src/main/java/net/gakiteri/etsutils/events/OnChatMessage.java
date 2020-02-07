package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatMessage implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent event) {

        String msg = event.getMessage();
        DataPlayer dataPlayer = new MngDatabase().getPlayer(event.getPlayer().getUniqueId());

        msg = msg.replace("&&", "§");

        String display = new MngDatabase().getRank(dataPlayer).getDisplay();

        event.setFormat("<" + display + "%1$s> %2$s");
        event.setMessage(msg);
    }
}
