package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LaborCommand extends ComplexCommand {

    public LaborCommand() {
        super("labor");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("labor")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            CustomUser user = UserDBHandler.getPlayer((Player) sender);
            player.sendMessage(ChatColor.YELLOW + "You currently have " + ChatColor.GRAY + "[" + ChatColor.GOLD + (user.getLabor()) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]" + ChatColor.YELLOW + " labor!");

            return true;
        }
        return false;
    }
}
