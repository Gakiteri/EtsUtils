package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CmdPlayer implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 1) {
            if (Database.canConnect && !new MngDatabase().hasPlayer(args[0])) {
                sender.sendMessage(ChatColor.RED + "The player " + args[0] + " does not exist");
                return true;
            }
        }

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "<player> get rank",
                    "<player> set rank <rank>",
                    "<player> get balance",
                    "<player> set balance <+|- amount>",
                    "<player> get pvp",
                    "<player> set pvp on|off"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else if (args.length == 3) {
            String playerName = args[0];
                if (args[1].equals("get")) {
                    switch (args[2]) {
                        case "balance":
                            if (Database.canConnect) {
                                sender.sendMessage(ChatColor.GREEN + playerName + " has a balance of " + new MngDatabase().getPlayer(playerName).getBalance());
                            }
                            break;
                        case "pvp":
                            if (Database.canConnect) {
                                sender.sendMessage(ChatColor.GREEN + playerName + " have their PvP is " + (new MngDatabase().getPlayer(playerName).getPvp() ? "on" : "off"));
                            }
                            break;
                        case "rank":
                            if (Database.canConnect) {
                                sender.sendMessage(ChatColor.GREEN + playerName + " is a(n) " + new MngDatabase().getRank(new MngDatabase().getPlayer(playerName)).getName());
                            }
                            break;
                        default:
                            sender.sendMessage(ChatColor.RED + "Invalid attribute, please enter a valid attribute");
                            break;
                    }
                    return true;
                }
        } else if (args.length == 4) {
            String playerName = args[0];
            if (args[1].equals("set")) {
                switch (args[2]) {
                    case "balance":
                        if (Database.canConnect) {

                        }
                        break;
                    case "pvp":
                        if (Database.canConnect) {

                        }
                        break;
                    case "rank":
                        if (Database.canConnect) {

                        }
                        break;
                    default:
                        sender.sendMessage(ChatColor.RED + "Invalid attribute, please enter a valid attribute");
                        break;
                }
                return true;
            }
        }

        sender.sendMessage(ChatColor.RED + "Error executing command, please check you used the correct syntax and try again");
        return false;
    }
}
