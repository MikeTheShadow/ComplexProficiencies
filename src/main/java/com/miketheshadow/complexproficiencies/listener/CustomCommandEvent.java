package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomCommandEvent implements CommandExecutor
{
    private final ComplexProficiencies complexProficiencies;

    public CustomCommandEvent(ComplexProficiencies complexProficiencies)
    {
        this.complexProficiencies = complexProficiencies;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("weaponsmithinggui"))
        {
            if (!(sender instanceof Player)) return false;
            GenericGUI genericGUI = new GenericGUI((Player) sender, BaseCategories.weaponsmithingItems(), "Weaponsmithing",18,false);
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("getitemtype"))
        {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            ItemStack item = new ItemStack(Material.valueOf(player.getItemInHand().getType().toString()));
            player.sendMessage(item.toString());
            if(item.hasItemMeta())
            {
                player.sendMessage(item.getItemMeta().getDisplayName());
            }
            else player.sendMessage("Item has no meta!");
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("addrecipe"))
        {
            if(!(sender instanceof Player)) return false;
            GenericGUI genericGUI = new GenericGUI((Player)sender, BaseCategories.getAllItems(),"Recipe Builder",54,true);
            return true;
        }
        return false;
    }

}
