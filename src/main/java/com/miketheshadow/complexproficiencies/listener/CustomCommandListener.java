package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CustomCommandListener implements CommandExecutor {
    private final ComplexProficiencies complexProficiencies;

    private static boolean reset = false;
    private static boolean active= false;

    public CustomCommandListener(ComplexProficiencies complexProficiencies) {
        this.complexProficiencies = complexProficiencies;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("opengui")) {
            if (!(sender instanceof Player)) return false;
            if ( !(args.length == 1 || args.length == 2)) {
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
            if(category == null) {
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
        else if(cmd.getName().equalsIgnoreCase("resetdb")) {
            if(!active && ComplexProficiencies.levelConfig.getBoolean("reset")) {
                active = true;
                Bukkit.broadcastMessage(ChatColor.RED + "DELETING ALL USER DATA IN COMPLEX PROFICIENCIES in 30s RUN THIS COMMAND AGAIN TO CANCEL!");
                Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.complexProficiencies, () -> {
                    if(active) {
                        Bukkit.broadcastMessage(ChatColor.RED + "REMOVING PLAYER DATA...");
                        List<CustomUser> userList = UserDBHandler.getAllPlayers();
                        for (CustomUser user: userList) {
                            UserDBHandler.removePlayer(user);
                        }
                        Bukkit.broadcastMessage(ChatColor.RED + "COMPLETE!");
                    }
                }, 600);
            } else if(active) {
                Bukkit.broadcastMessage(ChatColor.RED + "DEACTIVATING DATA REMOVAL!");
                active = false;
            } else {
                sender.sendMessage(ChatColor.RED + "Reset is disabled!");
            }
            return true;
        } else if(cmd.getName().equalsIgnoreCase("labor")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            CustomUser user = UserDBHandler.getPlayer((Player) sender);
            player.sendMessage(ChatColor.YELLOW + "You currently have " + ChatColor.GRAY + "[" + ChatColor.GOLD + (user.getLabor()) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]" + ChatColor.YELLOW + " labor!");

            return true;
        }
        return false;
    }

}
