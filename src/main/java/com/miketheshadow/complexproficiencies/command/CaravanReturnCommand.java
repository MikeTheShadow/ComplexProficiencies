package com.miketheshadow.complexproficiencies.command;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.caravan.Caravan;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CaravanReturnCommand extends ComplexCommand
{
    public CaravanReturnCommand() {
        super("caravanreturn");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args)
    {
        if(cmd.getName().equalsIgnoreCase("caravanreturn")) {
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
                double multiplier = Math.sqrt(Math.pow(x1 - x2,2)+Math.pow(z1-z2,2))/1000;
                int laborValue = (int)((Caravan.CRAFTING_COST + Caravan.RETURN_COST) * (multiplier/2));
                value += laborValue;
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
