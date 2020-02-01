package com.miketheshadow.complexproficiencies.listeners;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
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
            if(!(sender instanceof Player)) return false;
            GenericGUI genericGUI = new GenericGUI((Player)sender, BaseCategories.getWeaponsmithing(),"Weaponsmithing");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("getitemtype"))
        {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            player.sendMessage(player.getItemInHand().getType().toString());
            return true;
        }
        return false;
    }

}
