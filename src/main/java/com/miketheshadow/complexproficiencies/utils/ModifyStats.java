package com.miketheshadow.complexproficiencies.utils;

import de.tr7zw.nbtapi.NBTCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModifyStats
{
    public static NBTCompound modifyWeapons(NBTCompound container,float modifier)
    {
        NBTCompound compound,loreCompound;
        List<String> loreContainer;
        try
        {
            compound = container.getCompound("tag");
            loreCompound = container.getCompound("tag").getCompound("display");
            loreContainer = loreCompound.getStringList("Lore");
            if(!loreContainer.contains(ChatColor.RED + "Sword")) return container;
        }
        catch (Exception e)
        {
            return container;
        }
        String[] quickList = new String[]{"MMOITEMS_ATTACK_DAMAGE","MMOITEMS_CRITICAL_STRIKE_CHANCE","MMOITEMS_CRITICAL_STRIKE_POWER"};
        for(String stat : quickList)
        {
            compound.setInteger(stat, + (compound.getInteger(stat) + (int)(compound.getInteger(stat) * modifier)));
        }
        for (int i = 0; i < loreContainer.size();i++)
        {
            if(loreContainer.get(i).contains("Attack Damage"))
            {
                loreContainer.set(i, ChatColor.GRAY + "➸ Attack Damage: " + ChatColor.WHITE + compound.getInteger("MMOITEMS_ATTACK_DAMAGE"));
            }
            if(loreContainer.get(i).contains("Crit Strike Chance"))
            {
                loreContainer.set(i,ChatColor.GRAY + "■ Crit Strike Chance: +" + ChatColor.WHITE + compound.getInteger("MMOITEMS_CRITICAL_STRIKE_CHANCE") + "%");
            }
            if(loreContainer.get(i).contains("Crit Strike Power"))
            {
                loreContainer.set(i,ChatColor.GRAY + "■ Crit Strike Power: +" + ChatColor.WHITE + compound.getInteger("MMOITEMS_CRITICAL_STRIKE_POWER") + "%");
            }
        }
        //merge the first compound
        compound.mergeCompound(loreCompound);
        //merge into the second
        container.mergeCompound(compound);
        return container;
    }

}
