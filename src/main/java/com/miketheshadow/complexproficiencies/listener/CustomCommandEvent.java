package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.gui.RecipeGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            GenericGUI genericGUI = new GenericGUI((Player) sender, BaseCategories.weaponsmithingItems(), "Weaponsmithing",18);
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("getitemtype"))
        {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            player.sendMessage(player.getItemInHand().getType().toString());
            return true;
        }
        else if (cmd.getName().equalsIgnoreCase("addrecipe"))
        {
            if(!(sender instanceof Player)) return false;
            RecipeGUI recipeGUI = new RecipeGUI((Player)sender, BaseCategories.weaponsmithingItems(),"Recipe Builder",54);
            return true;
        }
        return false;
    }

}
