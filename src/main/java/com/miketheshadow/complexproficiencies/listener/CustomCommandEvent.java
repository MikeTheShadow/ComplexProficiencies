package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataHolder;

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
        if (cmd.getName().equalsIgnoreCase("craftinggui"))
        {
            if (!(sender instanceof Player)) return false;
            if(args.length != 1)
            {
                return false;
            }
            Player player  = (Player)sender;
            GenericGUI genericGUI;
            switch (args[0].toLowerCase())
            {
                case "armorsmithing":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.armorsmithingItems(), "armorsmithing",18,false);
                    break;
                case "cooking":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.cookingItems(), "cooking",18,false);
                    break;
                    /* uncomment if fishing needs recipes
                case "fishing":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.fishingItems(), "fishing",18,false);
                    break;
                    */
                case "handicrafts":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.handicraftItems(), "handicrafts",18,false);
                    break;
                case "metalworking":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.metalworkingItems(), "metalworking",18,false);
                    break;
                case "leatherworking":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.leatherworkingItems(), "leatherworking",18,false);
                    break;
                    /* same comment as above
                case "mining":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.weaponsmithingItems(), "mining",18,false);
                    break;
                     */
                case "weaponsmithing":
                    genericGUI = new GenericGUI((Player) sender, BaseCategories.weaponsmithingItems(), "Weaponsmithing",18,false);
                    break;
                default:
                    player.sendMessage("Error! GUI \"" + args[0] + "\" does not exist!");
                    break;
            }
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("getitemtype"))
        {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            ItemStack item = new ItemStack(Material.valueOf(player.getItemInHand().getType().toString()));
            player.sendMessage(item.toString());
            player.sendMessage(NBTItem.convertItemtoNBT(item).toString());
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
