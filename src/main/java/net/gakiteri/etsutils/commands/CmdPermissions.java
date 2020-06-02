package net.gakiteri.etsutils.commands;

import net.gakiteri.etsutils.Variables;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CmdPermissions implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Variables.defCmdPermissions) {
            sender.sendMessage(ChatColor.RED + "This command is disabled and that's probably a bad thing...");
            return true;
        }



        return false;
    }
}
