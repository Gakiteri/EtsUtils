package net.gakiteri.etsutils.events;

import net.gakiteri.etsutils.functions.MngDatabase;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import java.util.UUID;
import static org.bukkit.Bukkit.getServer;

public class OnPvp implements Listener {

    @EventHandler
    public void onPvp(EntityDamageByEntityEvent event) {

        // Check if entity is a player
        if (event.getEntity() instanceof Player) {
            // Get entity data
            Entity attacker = event.getDamager();
            UUID receiverUUID = ((Player) event.getEntity()).getPlayer().getUniqueId();
            UUID attackerUUID = null;
            boolean fireStrike = false;

            // Check what if attacker is Player
            if (attacker instanceof Player) {
                attackerUUID = ((Player) attacker).getPlayer().getUniqueId();
            } else if (attacker instanceof Projectile && ((Projectile) attacker).getShooter() instanceof Player) {
                attackerUUID = ((Player) ((Projectile) attacker).getShooter()).getPlayer().getUniqueId();
            } else if (attacker instanceof ThrownPotion && ((ThrownPotion) attacker).getShooter() instanceof Player) {
                attackerUUID = ((Player) ((ThrownPotion) attacker).getShooter()).getPlayer().getUniqueId();
            } else if (attacker instanceof AreaEffectCloud && ((AreaEffectCloud) attacker).getSource() instanceof Player) {
                attackerUUID = ((Player) ((AreaEffectCloud) attacker).getSource()).getPlayer().getUniqueId();
            } else if (attacker instanceof Firework) {
                fireStrike = true;
            } else if (attacker instanceof LightningStrike) {
                fireStrike = true;
            }

            boolean receiverPvp = new MngDatabase().getPlayer(receiverUUID).getPvp();

            // Check if damaged is has to be cancelled
            if (attackerUUID == null) {
                if (!receiverPvp && fireStrike) {
                    event.setCancelled(true);
                }
            } else {
                // Check if player hurting themselves
                if (!receiverUUID.equals(attackerUUID)) {
                    boolean attackerPvp = new MngDatabase().getPlayer(attackerUUID).getPvp();

                    if (!attackerPvp) {
                        event.setCancelled(true);
                        getServer().getPlayer(attackerUUID).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "You have PVP off!"));
                    } else if (!receiverPvp) {
                        event.setCancelled(true);
                        getServer().getPlayer(attackerUUID).spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + getServer().getPlayer(receiverUUID).getName() + " has PVP off!"));
                    }
                }
            }
        }
    }
}
