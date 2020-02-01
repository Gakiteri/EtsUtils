package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.data.DataRank;
import net.gakiteri.etsutils.functions.MngDatabase;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CmdRank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0) {

        } else if (args.length == 1) {
            if (args[0].equals("list")) {
                ArrayList<DataRank> rankList = new MngDatabase().getRankList();

                if (!rankList.isEmpty()) {
                    sender.sendMessage(ChatColor.GOLD + "Available ranks:");
                    rankList.forEach(i -> {
                        sender.sendMessage(ChatColor.BLUE
                                + "- Permission level: " + i.getLevel()
                                + " Name: " + i.getName()
                                + " Display: " + ChatColor.RESET + i.getDisplay());
                    });
                } else {
                    sender.sendMessage(ChatColor.BLUE + "There are no ranks");
                }

                return true;
            }
        } else if (args.length == 2) {
            if (args[0].equals("remove")) {
                if (new MngDatabase().removeRank(args[1].toLowerCase())) {
                    sender.sendMessage(ChatColor.GREEN + "Rank successfully deleted");
                } else {
                    sender.sendMessage(ChatColor.RED + "Rank could not be deleted");
                }
                return true;
            }
        } else if (args.length == 4) {
            if (args[0].equals("add")) {
                DataRank rank = new DataRank();

                rank.setLevel(Integer.parseInt(args[2]));
                rank.setName(args[1]);
                rank.setDisplay(args[3].replace("&&", "§"));

                if (new MngDatabase().addRank(rank)) {
                    sender.sendMessage(ChatColor.GREEN + "Rank successfully added");
                } else {
                    sender.sendMessage(ChatColor.RED + "Rank could not be added");
                }
                return true;
            }
        }


        return false;
    }

}
