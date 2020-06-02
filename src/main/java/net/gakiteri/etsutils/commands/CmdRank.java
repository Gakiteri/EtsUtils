package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.Variables;
import net.gakiteri.etsutils.data.DataPlayer;
import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.data.Database;
import net.gakiteri.etsutils.functions.MngConfig;
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

        if (!Variables.defCmdRank) {
            sender.sendMessage(ChatColor.RED + "This command is disabled");
            return true;
        }

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "list",
                    "add <rank> <display>",
                    "modify <rank> <display>",
                    "remove <rank>",
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
                                + " Name: " + i.getName()
                                + " Display: " + ChatColor.RESET + i.getDisplay());
                    });
                } else {
                    sender.sendMessage(ChatColor.RED + "There are no ranks");
                }
                return true;
            } else {
                String playerName = args[0];
                if (!(playerName.equals("modify") || playerName.equals("remove") || playerName.equals("add"))) {
                    if (Database.canConnect) {
                        if (new MngDatabase().hasPlayer(playerName)) {
                            DataPlayer dataPlayer = new MngDatabase().getPlayer(playerName);
                            if (new MngDatabase().hasRank(dataPlayer)) {
                                DataRank dataRank = new MngDatabase().getRank(dataPlayer);
                                sender.sendMessage(ChatColor.BLUE + playerName + " has the rank " + dataRank.getName());
                            } else {
                                sender.sendMessage(ChatColor.RED + playerName + " has no rank");
                            }
                        } else {
                            sender.sendMessage(ChatColor.RED + "The player " + playerName + " does not exist");
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            }
        } else if (args.length == 2) {
            if (args[0].equals("remove")) {
                if (Database.canConnect) {
                    if (new MngDatabase().removeRank(args[1].toLowerCase())) {
                        sender.sendMessage(ChatColor.GREEN + "Rank successfully deleted");
                        MngConfig.save();
                    } else {
                        sender.sendMessage(ChatColor.RED + "Rank could not be deleted");
                    }
                    return true;
                }
            }
        } else if (args.length == 3) {
            if (args[0].equals("add")) {
                String rankName = args[1].toLowerCase();
                DataRank rank = new DataRank();

                rank.setName(rankName);
                rank.setDisplay(args[2].replace("&&", "§") + "§r");

                if (Database.canConnect) {
                    if (new MngDatabase().addRank(rank)) {
                        sender.sendMessage(ChatColor.GREEN + "Rank successfully added");
                        MngConfig.save();
                    } else {
                        sender.sendMessage(ChatColor.RED + "Rank could not be added");
                    }
                    return true;
                }
            } else if (args[0].equals("modify")) {
                String rankName = args[1].toLowerCase();

                if (Database.canConnect) {
                    if (new MngDatabase().hasRank(rankName)) {
                        DataRank rank = new MngDatabase().getRank(rankName);

                        rank.setName(rankName);
                        rank.setDisplay(args[2].replace("&&", "§") + "§r");

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
