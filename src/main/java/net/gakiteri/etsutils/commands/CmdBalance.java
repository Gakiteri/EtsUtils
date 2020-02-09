package net.gakiteri.etsutils.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CmdBalance implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            List<String> arguments = Arrays.asList(
                    "<player> get rank",
                    "<player> set rank <rank>",
                    "<player> get balance",
                    "<player> set balance <+|-> <amount>",
                    "<player> get pvp",
                    "<player> set pvp on|off"
            );
            sender.sendMessage(ChatColor.GOLD + "Available arguments:");
            arguments.forEach(i -> {
                sender.sendMessage(ChatColor.BLUE + " -> " + i);
            });
            return true;
        } else if (args.length == 1) {

        }

        return false;
    }
}
