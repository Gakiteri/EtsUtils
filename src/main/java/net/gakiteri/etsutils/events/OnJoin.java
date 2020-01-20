package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.PlayerData;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        PlayerData playerData = new MngDatabase().getPlayer(uuid);

        playerData.setOnline(true);



    }
}
