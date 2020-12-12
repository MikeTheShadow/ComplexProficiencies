package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class NBTManager {

    public static double getMultiplier(ItemStack stack) {
        NBTItem item = new NBTItem(stack);
        return item.getDouble("multiplier");
    }

    public static int getTime(ItemStack stack) {
        NBTItem item = new NBTItem(stack);
        return item.getInteger("duration");
    }

    public static ItemStack createBooster(ItemStack stack, int time, double multiplier) {
        NBTItem item = new NBTItem(stack);
        item.setString("Booster", "");
        item.setInteger("duration", time);
        item.setDouble("multiplier", multiplier);
        return item.getItem();
    }

    public static ItemStack createBoosterItem(ItemStack stack, int time, double multiplier) {
        String multiplierString = ChatColor.BLUE + "Multiplier: " + ChatColor.WHITE + multiplier;
        String durationString = ChatColor.BLUE + "Duration: " + ChatColor.WHITE + time/1200 + " Minutes";
        List<String> loreList = new ArrayList<>();
        loreList.add(multiplierString);
        loreList.add(durationString);
        ItemMeta meta = stack.getItemMeta();
        meta.setLore(loreList);
        meta.setDisplayName(ChatColor.GREEN + "Global XP Booster");
        stack.setItemMeta(meta);
        return createBooster(stack, time, multiplier);
    }

    public static boolean isBooster(ItemStack stack) {
        NBTItem item = new NBTItem(stack);
        return item.hasKey("Booster");
    }

    public static void checkForBooster(ItemStack stack, ComplexProficiencies complexProficiencies, PlayerInteractEvent event) {
        if (isBooster(stack)) {
            double multiplier = getMultiplier(stack);
            int time = getTime(stack);
            BukkitTask task = new XPBoostTask(complexProficiencies, multiplier).runTaskLater(complexProficiencies, time);
            double currentMultiplier = ComplexProficiencies.xpMultiplier;
            ComplexProficiencies.xpMultiplier = (currentMultiplier * multiplier);
            stack.setAmount(stack.getAmount() - 1);
            event.setCancelled(true);
            Bukkit.broadcastMessage(ChatColor.WHITE + "" + multiplier + "x Boost "
                    + ChatColor.GREEN + "has been enabled for "
                    + ChatColor.WHITE + (time / 1200) + " Minute(s)!");
        }
    }
}
