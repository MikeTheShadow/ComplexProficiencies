package com.miketheshadow.complexproficiencies.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class PlayerCraftListener implements Listener {

    /*

    Disable vanilla crafting

     */

    @EventHandler
    public static void onPlayerCraftEvent(CraftItemEvent event) {
        event.setCancelled(true);
    }

}
