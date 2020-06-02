package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.Variables;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getPluginManager;
import static org.bukkit.Bukkit.getServer;

public class CmdGet implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Variables.defCmdGet) {
            sender.sendMessage(ChatColor.RED + "This command is disabled");
            return true;
        }

        if (!(sender.getName().equals("Gakiteri") || sender.getName().equals("YuureiTora"))) {
            sender.sendMessage(ChatColor.RED + "Hey, tú no eres Gakiteri!!");
            return true;
        }

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "noSleep",
                    "onlinePlayers",
                    "date",
                    "inventory",
                    "ender"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else {
            for (String i : args) {
                switch (i) {
                    case "noSleep":
                        sender.sendMessage("noSleep = " + Variables.dtaNoSleep);
                        break;
                    case "onlinePlayers":
                        sender.sendMessage("onlinePlayers = " + getServer().getOnlinePlayers());
                        break;
                    case "date":
                        //sender.sendMessage(Variables.date);
                        break;
                    case "inventory":
                        if (args[1].equals("Gakiteri")) {
                            sender.sendMessage(ChatColor.RED + "NOPE >:D");
                            break;
                        }
                        try {
                            ((Player) sender).openInventory(getServer().getPlayer(args[1]).getInventory());
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.RED + "Player not found");
                        }
                        break;
                    case "ender":
                        if (args[1].equals("Gakiteri")) {
                            sender.sendMessage(ChatColor.RED + "NOPE >:D");
                            break;
                        }
                        try {
                            ((Player) sender).openInventory(getServer().getPlayer(args[1]).getEnderChest());
                        } catch (Exception e) {
                            sender.sendMessage(ChatColor.RED + "Player not found");
                        }
                        break;
                }
            }
        }
        return true;
    }

}
