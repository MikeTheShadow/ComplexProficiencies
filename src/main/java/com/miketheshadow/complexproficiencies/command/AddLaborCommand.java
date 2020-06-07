package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.LaborThread;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddLaborCommand extends ComplexCommand {


    public AddLaborCommand() {
        super("complexaddlabor");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(!(sender instanceof Player)) return false;
        if(args.length != 2) return false;
        Player player = Bukkit.getPlayer(args[0]);
        int amount = Integer.parseInt(args[1]);
        if(player == null) {
            sender.sendMessage("Player does not exist!");
            return true;
        } else {
           CustomUser user = UserDBHandler.getPlayer(player);
           if(user.getLabor() + amount > LaborThread.cap) {
               player.sendMessage(ChatColor.RED + "You cannot go over your labor cap!");
           } else {
               user.setLabor(user.getLabor() + amount);
               player.sendMessage(ChatColor.YELLOW + "You gained "
                       + ChatColor.GOLD
                       + amount
                       + ChatColor.YELLOW
                       + " labor! "
                       + ChatColor.GRAY + "[" + ChatColor.GOLD + (user.getLabor()+amount) + ChatColor.GRAY + "/" + ChatColor.GOLD + LaborThread.cap + ChatColor.GRAY + "]");
           }

        }
        return true;
    }
}
