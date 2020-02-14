package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.data.DataPlayer;
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

        String playerName = "";
        DataPlayer dataPlayer = new DataPlayer();

        if (args.length >= 1) {
            if (Database.canConnect) {
                if (new MngDatabase().hasPlayer(args[0])) {
                    playerName = args[0];
                    dataPlayer = new MngDatabase().getPlayer(playerName);
                } else {
                    sender.sendMessage(ChatColor.RED + "The player " + args[0] + " does not exist");
                    return true;
                }
            }
        }

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "<player> get balance",
                    "<player> set balance <+|- amount>",
                    "<player> get rank",
                    "<player> set rank <rank>",
                    "<player> remove rank",
                    "<player> get pvp",
                    "<player> set pvp on|off"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else if (args.length == 3) {
                if (args[1].equals("get")) {
                    switch (args[2]) {
                        case "balance":
                            int balance = -1;
                            if (Database.canConnect) {
                                balance = dataPlayer.getBalance();
                            }
                            sender.sendMessage(ChatColor.GREEN + playerName + " has a balance of " + balance);
                            break;
                        case "pvp":
                            boolean pvp = false;
                            if (Database.canConnect) {
                                pvp = dataPlayer.getPvp();
                            }
                            sender.sendMessage(ChatColor.GREEN + playerName + " have their PvP is " + (pvp ? "on" : "off"));
                            break;
                        case "rank":
                            String rank = "";
                            if (Database.canConnect) {
                                rank = new MngDatabase().getRank(dataPlayer).getName();
                            }
                            sender.sendMessage(ChatColor.GREEN + playerName + " has the rank " + rank);
                            break;
                        default:
                            sender.sendMessage(ChatColor.RED + "Invalid attribute, please enter a valid attribute");
                            break;
                    }
                    return true;
                } else if (args[1].equals("remove")) {
                    if ("rank".equals(args[2])) {
                        dataPlayer.setRank(null);
                        if (Database.canConnect) {
                            new MngDatabase().updatePlayer(dataPlayer);
                        }
                        sender.sendMessage(ChatColor.GREEN + playerName +"'s rank has been removed and the default rank has been set");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Invalid attribute, please enter a valid attribute");
                    }
                    return true;
                }
        } else if (args.length == 4) {
            if (args[1].equals("set")) {
                switch (args[2]) {
                    case "balance":

                        if (Database.canConnect) {

                        }

                        break;
                    case "pvp":
                        if (args[3].equals("on") || args[3].equals("off")) {
                            if (args[3].equals("on")) {
                                dataPlayer.setPvp(true);
                            } else {
                                dataPlayer.setPvp(false);
                            }
                            if (Database.canConnect) {
                                new MngDatabase().updatePlayer(dataPlayer);
                            }
                            sender.sendMessage(ChatColor.GREEN + playerName + "'s PvP turned " + args[3]);
                        } else {
                            sender.sendMessage(ChatColor.RED + "Please enter a valid value");
                        }
                        return true;
                    case "rank":
                        String rankName = args[3];
                        if (Database.canConnect) {
                            if (new MngDatabase().hasRank(rankName)) {
                                dataPlayer.setRank(new MngDatabase().getRank(rankName));
                                new MngDatabase().updatePlayer(dataPlayer);
                                sender.sendMessage(ChatColor.GREEN + playerName + " was added to " + rankName);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Rank does not exist, please enter a valid rank");
                                return true;
                            }
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
