package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.caravan.Caravan;
import com.miketheshadow.complexproficiencies.crafting.Category;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.CategoryDBHandler;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
                Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> {
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
        } else if(cmd.getName().equalsIgnoreCase("prof")) {
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
            } return false;
        } else if(cmd.getName().equalsIgnoreCase("proftop")) {
            if(args.length != 1) return false;
            List<CustomUser> userList = UserDBHandler.getAllPlayers();
            if(userList.get(0).getProfessions().get(args[0]) == null) {
                sender.sendMessage(ChatColor.RED + "That prof doesn't exist!");
                return true;
            }
            List<CustomUser> topUsers = new ArrayList<>();
            for (CustomUser user: userList) {
                if(topUsers.size() < 5)topUsers.add(user);
                for (CustomUser u: topUsers) {
                    if(user.getProfessions().get(args[0]) > u.getProfessions().get(args[0]) && !topUsers.contains(user)) {
                        topUsers.set(topUsers.indexOf(u),user);
                        break;
                    }
                }
            }
            sender.sendMessage("§5" + StringUtils.repeat("~",20));
            sender.sendMessage(ChatColor.DARK_PURPLE + args[0].substring(0, 1).toUpperCase() + args[0].substring(1));
            for (CustomUser user: topUsers) {
                sender.sendMessage(ChatColor.GOLD + user.getName() + ChatColor.GRAY +" : " + ChatColor.GREEN + user.getLevelFromTotal(user.getProfessions().get(args[0])));
            }
            return true;
            //usage: /<caravancreate> [laborCost] [moneyCost] [playerName]
        } else if(cmd.getName().equalsIgnoreCase("caravancreate")) {
            if(args.length != 3) return false;
            Player player = Bukkit.getPlayer(args[2]);
            if(player == null) return false;
            CustomUser user = UserDBHandler.getPlayer(player);
            int moneyCost = Integer.parseInt(args[0]);
            String zoneName = args[1];
            if(user.getLabor() < Caravan.CRAFTING_COST) {
                player.sendMessage(ChatColor.RED + "You don't have enough labor to do this!");
                return true;
            } else if(!ComplexProficiencies.econ.hasAccount(player)) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            } else if(ComplexProficiencies.econ.getBalance(player) < moneyCost) {
                player.sendMessage(ChatColor.RED + "You don't have enough money!");
                return true;
            }
            ComplexProficiencies.econ.withdrawPlayer(player,moneyCost);
            user.setLabor(user.getLabor() - Caravan.CRAFTING_COST);
            user.addExperience("commerce",Caravan.CRAFTING_COST,player);
            UserDBHandler.updatePlayer(user);
            Caravan.createCaravan(player,zoneName,String.valueOf(moneyCost));
            return true;
        } else if(cmd.getName().equalsIgnoreCase("caravanreturn")) {
            if(args.length != 2) return false;
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) return false;
            if(player.getVehicle() == null) {
                player.sendMessage(ChatColor.RED + "You must be riding a caravan to turn it in!");
                return true;
            }
            if(player.getVehicle().getType() != EntityType.DONKEY) {
                player.sendMessage(ChatColor.RED + "You must be riding a caravan to turn it in!");
                return true;
            }
            Donkey donkey = (Donkey) player.getVehicle();
            CustomUser user = UserDBHandler.getPlayer(player);
            List<String> lore = donkey.getInventory().getItem(0).getItemMeta().getLore();
            if(!lore.get(4).equals(args[0])) {
                //Math.abs
                int x1 = Integer.parseInt(lore.get(1));
                int z1 = Integer.parseInt(lore.get(2));

                int x2 = (int) player.getLocation().getX();
                int z2 = (int) player.getLocation().getZ();

                int value = Integer.parseInt(lore.get(5));
                Bukkit.broadcastMessage("Base Value: " + value);
                double multiplier = Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(z1-z2,2))/1000;
                Bukkit.broadcastMessage("Base: " + value);
                int laborValue = (int)((Caravan.CRAFTING_COST + Caravan.RETURN_COST) * (multiplier/2));
                value += laborValue;
                Bukkit.broadcastMessage("Total: " + value);
                if(user.getLabor() < Caravan.RETURN_COST) {
                    player.sendMessage(ChatColor.RED + "You don't have enough labor to turn this in!");
                    return true;
                }
                if(lore.get(0).equals(player.getName())) {
                    player.sendMessage(ChatColor.GOLD + "You turned in a caravan worth " +  ChatColor.GREEN  +"$" + value + ChatColor.GOLD + "!");
                    user.addExperience("commerce",Caravan.RETURN_COST,player);
                    user.setLabor(user.getLabor() - Caravan.RETURN_COST);
                    UserDBHandler.updatePlayer(user);
                    ComplexProficiencies.econ.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()),value);
                } else {
                    int valueCrafter = (int)(value * .2);
                    int valueStolen = (int)(value * .8);
                    CustomUser crafter = UserDBHandler.getPlayer(lore.get(0));
                    CustomUser thief = UserDBHandler.getPlayer(player);
                    if(thief.getLabor() < Caravan.RETURN_COST) {
                        player.sendMessage(ChatColor.RED + "You don't have enough labor to turn this in!");
                        return true;
                    }
                    thief.addExperience("commerce",Caravan.RETURN_COST,player);
                    thief.setLabor(user.getLabor() - Caravan.RETURN_COST);
                    UserDBHandler.updatePlayer(thief);
                    if(Objects.requireNonNull(Bukkit.getPlayer(crafter.getName())).isOnline()) {
                        Objects.requireNonNull(Bukkit.getPlayer(crafter.getName())).sendMessage(ChatColor.GOLD + "Someone turned in your caravan for " + ChatColor.GREEN + "$" + valueCrafter + ChatColor.GOLD + "!");
                    }
                    player.sendMessage(ChatColor.GOLD + "You turned in a caravan worth " +  ChatColor.GREEN  +"$" + valueStolen+ ChatColor.GOLD + "!");
                    ComplexProficiencies.econ.depositPlayer(Bukkit.getOfflinePlayer(UUID.fromString(crafter.getUid())),valueCrafter);
                    ComplexProficiencies.econ.depositPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()),valueStolen);
                }
                donkey.remove();
                return true;
            } else {
                player.sendMessage(ChatColor.RED + "You cannot turn in a caravan at the same place you purchased it from!");
            }
            return true;
        }
        return false;
    }

}
