package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddRecipeCommand extends ComplexCommand {

    public AddRecipeCommand() {
        super("addrecipe");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if (!(sender instanceof Player)) return false;
        if (args.length == 4) {
            Category category = CategoryDBHandler.getCategory("/" + args[3].toLowerCase());
            if(category == null)
            {
                sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[2]);
                return true;
            }
            GenericGUI.addRecipe((Player)sender,category.getTitle(),Integer.parseInt(args[0]),Integer.parseInt(args[1]),category.getTitle());
            return true;
        }
        else if(args.length == 3) {
            Category category = CategoryDBHandler.getCategory("/" + args[2].toLowerCase());
            if(category == null)
            {
                sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[2]);
                return true;
            }
            GenericGUI.addRecipe((Player)sender,category.getTitle(),Integer.parseInt(args[0]),Integer.parseInt(args[1]),category.getTitle());
            return true;
        }
        return false;
    }
}
