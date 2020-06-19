package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OpenGuiCommand extends ComplexCommand {

    public OpenGuiCommand() {
        super("opengui");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("opengui")) {
            if (!(sender instanceof Player)) return false;
            if ( !(args.length == 1 || args.length == 2)) {
                return false;
            }
            Category category = CategoryDBHandler.getCategory("/" + args[0].toLowerCase());
            Player player;
            if(args.length == 1)player = (Player)sender;
            else player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage(ChatColor.RED + "Player doesn't exist with name: " + args[1]);
                return true;
            }
            if(category == null) {
                sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[0]);
                return true;
            }
            GenericGUI.baseGUI(player,category.getTitle());
            return true;
        }
        return false;
    }
}
