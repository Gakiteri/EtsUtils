package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.PlayerData;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class OnLeave implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (Database.canConnect) {
            PlayerData playerData = new MngDatabase().getPlayer(uuid);
            playerData.setState("off");
            new MngDatabase().updatePlayer(playerData);
        }


    }


}
