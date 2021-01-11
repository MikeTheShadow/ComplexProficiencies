package com.miketheshadow.complexproficiencies.Boosters;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

public class BoosterUtil {

    static ComplexProficiencies complexProficiencies = ComplexProficiencies.INSTANCE;

    public static void runBooster(ComplexProficiencies complexProficiencies, ItemStack stack, NBTItem item) {
        double boost = BoosterNBT.getBoost(item);
        int time = BoosterNBT.getTime(item);

        increaseBoost(boost);

        BukkitTask task = new BoosterTask(complexProficiencies, boost).runTaskLater(complexProficiencies, time * 1200L);
        Bukkit.broadcastMessage(ChatColor.WHITE + "" + boost + "x Boost "
                + ChatColor.GREEN + "has been enabled for "
                + ChatColor.WHITE + (time) + " Minute(s)!");
    }

    public static void increaseBoost(double boost) {
        double currentBoost = ComplexProficiencies.boost;
        if (currentBoost == 1) {
            ComplexProficiencies.boost = boost;
        } else {
            ComplexProficiencies.boost = currentBoost + boost;
        }
    }

    public static void decreaseBoost(double boost) {
        double newBoost = ComplexProficiencies.boost - boost;
        ComplexProficiencies.boost = Math.max(newBoost, 1);
    }

    public static void setBoost(double boost, int time) {
        ComplexProficiencies.boost = boost;
        if (time < 1) return;
        BukkitTask task = new BoosterTask(complexProficiencies, boost).runTaskLater(complexProficiencies, time * 1200L);
    }
}
