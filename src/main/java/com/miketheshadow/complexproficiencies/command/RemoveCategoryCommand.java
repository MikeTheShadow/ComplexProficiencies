package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RemoveCategoryCommand extends ComplexCommand {

    public RemoveCategoryCommand() {
        super("removecategory");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("removecategory")) {
            if (!(sender instanceof Player)) return false;
            if (args.length != 1) {
                return false;
            }
            Category category = new Category(args[0],"","");
            if(!CategoryDBHandler.removeCategory(category)){
                sender.sendMessage(ChatColor.RED + "Error! Category does not exist!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Removed category: " + ChatColor.GOLD + args[0]);
            return true;
        }
        return false;
    }
}
