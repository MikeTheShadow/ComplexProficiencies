package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CategoryDBHandler;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomCommandListener implements CommandExecutor {
    private final ComplexProficiencies complexProficiencies;

    public CustomCommandListener(ComplexProficiencies complexProficiencies) {
        this.complexProficiencies = complexProficiencies;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("craftinggui")) {
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
        }
        else if (cmd.getName().equalsIgnoreCase("getitemtags")) {
            if (!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            ItemStack item = player.getItemInHand();
            NBTItem nbtItem = new NBTItem(item);
            player.sendMessage(NBTItem.convertItemtoNBT(item).toString());
            player.sendMessage(nbtItem.getKeys().toString());
            if (item.getItemMeta() != null) {
                player.sendMessage("NAME: " + item.getItemMeta().getDisplayName());
            }
            return true;
        } else if (cmd.getName().equalsIgnoreCase("addrecipe")) {
            if (!(sender instanceof Player)) return false;
            if (args.length != 2) return false;
            Player player = (Player) sender;
            try {
                Integer.parseInt(args[0]);
            } catch (Exception e) {
                player.sendMessage("\"" + args[0] + "\"" + " is not a number!");
                return true;
            }
            GenericGUI genericGUI = new GenericGUI((Player) sender, BaseCategories.getAllItems(), "Recipe Builder", 54, true, Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            return true;
        }
        return false;
    }

}
