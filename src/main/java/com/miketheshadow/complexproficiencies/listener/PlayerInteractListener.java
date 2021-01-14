package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.Boosters.BoosterNBT;
import com.miketheshadow.complexproficiencies.Boosters.BoosterUtil;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractListener implements Listener {

    ComplexProficiencies complexProficiencies = ComplexProficiencies.INSTANCE;
    double boost = ComplexProficiencies.boost;

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.hasItem()) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();
            NBTItem item = new NBTItem(stack);
            if (BoosterNBT.isBooster(item)) {
                event.setCancelled(true);
                if (boost == 1) {
                    BoosterUtil.runBooster(complexProficiencies, stack, item);
                    player.sendMessage(ChatColor.GREEN + "You have activated a booster!");
                    player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1f, 1f);
                } else {
                    player.sendMessage(ChatColor.RED + "Global Boost is at maximum value!");
                }
            }
        }
    }
}
