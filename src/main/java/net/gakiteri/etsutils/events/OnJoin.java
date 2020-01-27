package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.data.PlayerData;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws SQLException {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();


        getServer().broadcastMessage("onJoin access 1");
        PlayerData playerData = new MngDatabase().getPlayer(uuid);

        playerData.setOnline(true);

        getServer().broadcastMessage("onJoin access 2");
        new MngDatabase().updatePlayer(playerData);

        getServer().broadcastMessage("database updated");

    }
}
