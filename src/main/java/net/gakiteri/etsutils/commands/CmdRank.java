package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CmdRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "list",
                    "remove <rank>",
                    "add <rank> <permission> <display>",
                    "modify <rank> <permission> <display>",
                    "<player>"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else if (args.length == 1) {
            if (args[0].equals("list")) {
                ArrayList<DataRank> rankList = new ArrayList<>();
                if (Database.canConnect) {
                    rankList = new MngDatabase().getRankList();
                }
                if (!rankList.isEmpty()) {
                    sender.sendMessage(ChatColor.GOLD + "Available ranks:");
                    rankList.forEach(i -> {
                        sender.sendMessage(ChatColor.BLUE
                                + "- Permission level: " + i.getLevel()
                                + " Name: " + i.getName()
                                + " Display: " + ChatColor.RESET + i.getDisplay());
                    });
                } else {
                    sender.sendMessage(ChatColor.RED + "There are no ranks");
                }
                return true;
            } else {
                String playerName = args[0];
                if (Database.canConnect) {
                    if (new MngDatabase().hasPlayer(playerName)) {
                        DataPlayer dataPlayer = new MngDatabase().getPlayer(playerName);
                        if (new MngDatabase().hasRank(dataPlayer)) {
                            DataRank dataRank = new MngDatabase().getRank(dataPlayer);
                            sender.sendMessage(ChatColor.GREEN + playerName + "has the rank " + dataRank.getName() + " with the permission level of " + dataRank.getLevel());
                        } else {
                            sender.sendMessage(ChatColor.RED + playerName + " has no rank");
                        }
                        return true;
                    } else {
                        sender.sendMessage(ChatColor.RED + "The player " + playerName + " does not exist");
                    }
                }
            }
        } else if (args.length == 2) {
            if (args[0].equals("remove")) {
                if (Database.canConnect) {
                    if (new MngDatabase().removeRank(args[1])) {
                        sender.sendMessage(ChatColor.GREEN + "Rank successfully deleted");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Rank could not be deleted");
                    }
                    return true;
                }
            }
        } else if (args.length == 4) {
            if (args[0].equals("add")) {
                    DataRank rank = new DataRank();

                    rank.setLevel(Integer.parseInt(args[2]));
                    rank.setName(args[1]);
                    rank.setDisplay(args[3].replace("&&", "§") + "§r");

                if (Database.canConnect) {
                    if (new MngDatabase().addRank(rank)) {
                        sender.sendMessage(ChatColor.GREEN + "Rank successfully added");
                    } else {
                        sender.sendMessage(ChatColor.RED + "Rank could not be added");
                    }
                    return true;
                }
            } else if (args[0].equals("modify")) {
                String rankName = args[1];

                if (Database.canConnect) {
                    if (new MngDatabase().hasRank(rankName)) {
                        DataRank rank = new MngDatabase().getRank(rankName);

                        rank.setLevel(Integer.parseInt(args[2]));
                        rank.setName(rankName);
                        rank.setDisplay(args[3].replace("&&", "§") + "§r");

                        if (new MngDatabase().updateRank(rank)) {
                            sender.sendMessage(ChatColor.GREEN + "Rank successfully modified");
                        } else {
                            sender.sendMessage(ChatColor.RED + "Rank could not be modified");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "Rank does not exists");
                    }
                    return true;
                }
            }
        }

        sender.sendMessage(ChatColor.RED + "Error executing command, please check you used the correct syntax and try again");
        return false;
    }

}
