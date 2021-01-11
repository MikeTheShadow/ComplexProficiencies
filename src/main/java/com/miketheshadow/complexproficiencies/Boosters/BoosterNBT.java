package com.miketheshadow.complexproficiencies.Boosters;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoosterNBT {

    public static boolean isBooster(NBTItem item) {
        return item.hasKey("boost");
    }

    public static double getBoost(NBTItem item) {
        return item.getDouble("boost");
    }

    public static int getTime(NBTItem item) {
        return item.getInteger("time");
    }

    public static NBTItem createBoosterItem(NBTItem item, double boost, int minutes) {
        item.setDouble("boost", boost);
        item.setInteger("time", minutes);
        return item;
    }

    public static ItemStack createBoosterItemStack(double boost, int minutes) {
        ItemStack stack = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Global XP Booster");
        meta.setLore(createBoosterLore(boost, minutes));
        return stack;
    }

    public static List<String> createBoosterLore(double boost, int minutes) {
        List<String> lore = new ArrayList<>();
        String multiplierString = ChatColor.BLUE + "Multiplier: " + ChatColor.WHITE + boost;
        String durationString = ChatColor.BLUE + "Duration: " + ChatColor.WHITE + minutes + " Minute(s)";
        lore.add(multiplierString);
        lore.add(durationString);
        return lore;
    }
}
