package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CategoryDBHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CustomCommandListener implements CommandExecutor {
    private final ComplexProficiencies complexProficiencies;

    public CustomCommandListener(ComplexProficiencies complexProficiencies) {
        this.complexProficiencies = complexProficiencies;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("opengui")) {
            if (!(sender instanceof Player)) return false;
            if (args.length != 1) {
                return false;
            }
            Category category = CategoryDBHandler.getCategory("/" + args[0].toLowerCase());
            if(category == null)
            {
                sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[0]);
                return true;
            }
            GenericGUI.baseGUI((Player)sender,category.getTitle());
            return true;
        } else if(cmd.getName().equalsIgnoreCase("addcategory")) {
            if (!(sender instanceof Player)) return false;
            if (args.length != 1) {
                return false;
            }
            Category category = new Category(args[0],"","");
            if(!CategoryDBHandler.checkCategory(category)){
                sender.sendMessage(ChatColor.RED + "Error! Category exists!");
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Created new category: " + ChatColor.GOLD + args[0]);
            return true;
        }else if(cmd.getName().equalsIgnoreCase("addsubcategory")) {
            if (!(sender instanceof Player)) return false;
            if (args.length != 1) { return false; }
            Category category = CategoryDBHandler.getCategory("/" + args[0].toLowerCase());
            if(category == null)
            {
                sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[0]);
                return true;
            }
            GenericGUI.addCategory((Player)sender,category.getTitle());
            return true;
        }
        else if(cmd.getName().equalsIgnoreCase("removecategory")) {
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
        } else if (cmd.getName().equalsIgnoreCase("addrecipe")) {
            if (!(sender instanceof Player)) return false;
            if (args.length == 4)
            {
                Category category = CategoryDBHandler.getCategory("/" + args[3].toLowerCase());
                if(category == null)
                {
                    sender.sendMessage(ChatColor.RED + "Error! No category exists with the name: " + args[2]);
                    return true;
                }
                GenericGUI.addRecipe((Player)sender,category.getTitle(),Integer.parseInt(args[0]),Integer.parseInt(args[1]),category.getTitle());
                return true;
            }
            else if(args.length == 3)
            {
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
        return false;
    }

}
