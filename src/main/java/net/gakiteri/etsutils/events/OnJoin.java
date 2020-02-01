package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.UUID;
import static org.bukkit.Bukkit.getServer;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (Database.canConnect) {
            DataPlayer dataPlayer = new MngDatabase().getPlayer(uuid);

            dataPlayer.setName(getServer().getPlayer(uuid).getName());
            dataPlayer.setState("on");

            new MngDatabase().updatePlayer(dataPlayer);
        }

        getServer().broadcastMessage(ChatColor.GOLD + "Welcome " + player.getName());

    }
}
