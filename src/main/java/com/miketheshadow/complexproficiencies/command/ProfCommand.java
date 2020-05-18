package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ProfCommand extends ComplexCommand {

    public ProfCommand() {
        super("prof");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if(args.length == 1) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            CustomUser user = UserDBHandler.getPlayer(player);
            if(user.getProfessions().get(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "That prof doesn't exist!");
                return true;
            }
            int level = user.getLevelFromTotal(user.getProfessions().get(args[0].toLowerCase()));
            player.sendMessage(ChatColor.GOLD + "You are level: " + ChatColor.GREEN + level + ChatColor.GOLD +" in " + ChatColor.DARK_PURPLE + args[0]);
            return true;
        } else if(args.length == 2) {
            Player player = Bukkit.getPlayer(args[1]);
            CustomUser user = UserDBHandler.getPlayer(player);
            if(user.getProfessions().get(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "That prof doesn't exist!");
                return true;
            }
            int level = user.getLevelFromTotal(user.getProfessions().get(args[0].toLowerCase()));
            sender.sendMessage(ChatColor.GOLD + "They are level: " + ChatColor.GREEN + level + ChatColor.GOLD +" in " + ChatColor.DARK_PURPLE + args[0]);
            return true;
        }
        return false;
    }
}
