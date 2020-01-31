package com.miketheshadow.complexproficiencies.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class ItemCraftedEvent implements Listener
{

    @EventHandler
    public void onItemCraftedEvent(CraftItemEvent event)
    {
        //Bukkit.broadcastMessage((event.getRecipe().getResult().getType().toString()));
        event.setCancelled(true);
        event.getWhoClicked().sendMessage("You aren't high enough level to craft this!");
    }


}
