package net.gakiteri.etsutils.events;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;

import static org.bukkit.Bukkit.getServer;

public class OnSleep implements Listener {

    @EventHandler
    public void onEnterBed(PlayerBedEnterEvent event) {

        event.getPlayer().setSleepingIgnored(false);

        getServer().getOnlinePlayers().forEach(n -> {
            if (n != event.getPlayer() && !n.isSleeping()) {
                n.setSleepingIgnored(true);
            }
        });
    }

    @EventHandler
    public void onLeaveBed(PlayerBedLeaveEvent event) {

        Server server = getServer();
        Player player = event.getPlayer();
        long time = player.getWorld().getTime();

        if (time >= 0 && time <= 100) { // If morning
            server.broadcastMessage(player.getPlayerListName() + ChatColor.YELLOW + " ha dormido");

            // Resets everyone's ignored status
            server.getOnlinePlayers().forEach(n -> {
                n.setSleepingIgnored(false);
            });
        } else { // If night player is ignored as other players are still sleeping
            player.setSleepingIgnored(true);
        }

    }
}
