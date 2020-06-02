package net.gakiteri.etsutils.functions;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataPlayer;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class MngPerms {

    private static Plugin plugin = getPluginManager().getPlugin(Variables.pluginName);
    private static HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();


    /** ADD PERMISSIONS TO PLAYER**/
    public boolean addPermission(Player player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        permissions.put(player.getUniqueId(), attachment);

        return true;
    }

    /** REMOVE PERMISSIONS FROM PLAYER**/
    public boolean remPermission(Player player) {
        PermissionAttachment attachment = player.addAttachment(plugin);
        permissions.put(player.getUniqueId(), attachment);

        return true;
    }



}
