package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataLocationLog;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class OnJoin implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (Database.canConnect) {
            DataPlayer dataPlayer = new DataPlayer();

            if (new MngDatabase().hasPlayer(uuid)) { // If players exists on DB
                dataPlayer = new MngDatabase().getPlayer(uuid);
                dataPlayer.setState("on");
                new MngDatabase().updatePlayer(dataPlayer);
            } else { // If player not in DB
                dataPlayer.setUuid(uuid);
                dataPlayer.setName(player.getName());
                dataPlayer.setState("on");
                dataPlayer.setRank(new MngDatabase().getRank(Variables.defPlayerRank));

                new MngDatabase().addPlayer(dataPlayer);
            }

            // Shows rank on Tab list
            player.setPlayerListName(dataPlayer.getRank().getDisplay() +" "+ ChatColor.GRAY + dataPlayer.getName());

        }

        // Server name on tab
        List<String> headers = Arrays.asList(
                "§5▁§1▂§2▃§e▅§6▆§c▇ §f§l PKTCraft §c▇§6▆§e▅§2▃§1▂§5▁",
                "§c❤♥ §c§lPKTCraft§c ♥❤",
                "§7☠ §6§lPKTCraft§7 ☠",
                "¯\\_(§lツ§r)_/¯",
                "❀❁❀ PKTCraft ❀❁❀"
        );
        player.setPlayerListHeader(headers.get(new Random().nextInt(headers.size())));


    }
}
