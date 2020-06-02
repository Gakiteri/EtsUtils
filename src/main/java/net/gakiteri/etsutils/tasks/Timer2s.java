package net.gakiteri.etsutils.tasks;


import net.gakiteri.etsutils.EtsUtils;
import net.gakiteri.etsutils.Variables;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;
import static org.bukkit.Bukkit.getServer;

public class Timer2s extends BukkitRunnable {

    EtsUtils plugin;

    public Timer2s(EtsUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

        Server server = getServer();

        // Get overworld time
        long time = server.getWorlds().stream()
                .filter(world -> (!world.getName().contains("end")) || (!world.getName().contains("nether")))
                .findFirst().get().getTime();

        // Resets noSleep Variable if day (during first 5 seconds of day)
        if (time >= 0 && time <= 100) {
            Variables.dtaNoSleep = false;
        }


        // When dtaNoSleep is active and its night
        if (Variables.dtaNoSleep && (time >= 11800 && time <= 23000)) {
            if (server.getOnlinePlayers().size() > 0) {
                server.getOnlinePlayers().parallelStream().forEach(player -> {
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "O" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "☠ NO DUERMAS ☠" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "O"));
                });
            }
        }


    }

}
