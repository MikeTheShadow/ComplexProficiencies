package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Donkey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerVehicleListener implements Listener {

    @EventHandler
    public void playerEntersVehicle(VehicleEnterEvent event) {
        if(!(event.getEntered() instanceof Player)) return;
        if(event.getVehicle().getType() != EntityType.DONKEY)return;
        Player player = (Player) event.getEntered();
        Donkey donkey = (Donkey) event.getVehicle();
        if(donkey.getInventory().getItem(0) == null) {
            donkey.remove();
            return;
        }
        ItemStack specialItem = donkey.getInventory().getItem(0);
        ItemMeta meta = specialItem.getItemMeta();
        List<String> lore = meta.getLore();
        if(lore == null) return;
        if(lore.get(0).equals(player.getName())) {
            lore.set(3,"true");
        } else if(lore.get(3).equals("true")) {
            player.sendMessage(ChatColor.RED + "You cannot steal this caravan yet!");
            event.setCancelled(true);
        }



    }

    @EventHandler
    public void playerExitsVehicle(VehicleExitEvent event) {
        if(!(event.getExited() instanceof Player)) return;
        if(event.getVehicle().getType() != EntityType.DONKEY)return;
        Player player = (Player) event.getExited();
        Donkey donkey = (Donkey) event.getVehicle();
        ItemStack specialItem = donkey.getInventory().getItem(0);
        ItemMeta meta = specialItem.getItemMeta();
        List<String> lore = meta.getLore();
        assert lore != null;
        if(lore.get(0).equals(player.getName())) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(ComplexProficiencies.INSTANCE, () -> {
                if(donkey.getPassengers().contains(player)) return;
                lore.set(3,"false");
                meta.setLore(lore);
                specialItem.setItemMeta(meta);
                donkey.getInventory().setItem(0,specialItem);
            }, 600L);
        }
    }


}
