package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AddCategoryCommand extends ComplexCommand {

    public AddCategoryCommand() {
        super("addcategory");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, String[] args)
    {
            if (!(sender instanceof Player)) return false;
            if (args.length != 1)
            {
                return false;
            }
            Category category = new Category(args[0], "", "");
            if (!CategoryDBHandler.checkCategory(category))
            {
                sender.sendMessage(ChatColor.RED + "Error! Category exists!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Created new category: " + ChatColor.GOLD + args[0]);
            return true;
    }

}
