package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CmdPvp implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Variables.defCmdPvp) {
            sender.sendMessage(ChatColor.RED + "This command is disabled");
            return true;
        }

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "on",
                    "off",
                    "get <player>",
                    "set <player> on|off"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else if (args.length == 1) {
            String playerName = sender.getName();
            if (args[0].equals("on")) {
                setPvp(playerName, true, sender);
            } else if (args[0].equals("off")) {
                setPvp(playerName, false, sender);
            }
            return true;
        } else if (args.length == 2 && args[0].equals("get")) {
            String playerName = args[1];
            if (new MngDatabase().hasPlayer(playerName)) {
                DataPlayer dataPlayer = new MngDatabase().getPlayer(playerName);
                String status = dataPlayer.getPvp() ? "on" : "off";
                sender.sendMessage(ChatColor.BLUE + "The PvP of " + playerName + " is " + status);
            } else {
                sender.sendMessage(ChatColor.RED + "The player " + playerName + " does not exist");
            }
            return true;
        } else if (args.length == 3 && args[0].equals("set")) {
            String playerName = args[1];
            if (playerName.equals("@a")) {

            } else if (new MngDatabase().hasPlayer(playerName)) {
                if (args[2].equals("on")) {
                    setPvp(playerName, true, sender);
                } else if (args[2].equals("off")) {
                    setPvp(playerName, false, sender);
                }
            } else {
                sender.sendMessage(ChatColor.RED + "The player " + playerName + " does not exist");
            }
            return true;
        }

        return false;
    }

    private void setPvp(String playerName, boolean val, CommandSender sender) {
        DataPlayer dataPlayer = new MngDatabase().getPlayer(playerName);

        if (dataPlayer.getPvp() == val) {
            sender.sendMessage(ChatColor.RED + "PvP is already " + (dataPlayer.getPvp() ? "on" : "off"));
        } else {
            dataPlayer.setPvp(val);
            new MngDatabase().updatePlayer(dataPlayer);
            sender.sendMessage(ChatColor.GREEN + "PvP turned " + (val ? "on" : "off"));
        }
    }

}
