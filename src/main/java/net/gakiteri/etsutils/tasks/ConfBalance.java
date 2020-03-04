package net.gakiteri.etsutils.tasks;

import net.gakiteri.etsutils.EtsUtils;
import org.bukkit.scheduler.BukkitRunnable;

public class ConfBalance extends BukkitRunnable {

    EtsUtils plugin;

    public ConfBalance(EtsUtils plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {

		


/*
        Server server = getServer();

        // Resets noSleep Variable
        long time = Variables.timeOverworld;
        if (time >= 0 && time <= 1000) {
            Variables.noSleep = false;
        }


        // When noSleep is active
        if (Variables.noSleep) {
            // Shows message
            if (time >= 12000 && time <= 23000) {
                if (server.getOnlinePlayers().size() > 0) {
                    server.getOnlinePlayers().forEach(n -> {
                        n.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "O" + ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "NO DORMIR" + ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "O"));
                    });
                }
            }
        }
*/

    }

}

