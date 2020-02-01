package com.miketheshadow.complexproficiencies.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

public class ItemCraftedEvent implements Listener
{

    @EventHandler
    public void onItemCraftedEvent(CraftItemEvent event)
    {
        event.setCancelled(true);
        event.getWhoClicked().sendMessage("You aren't high enough level to craft this!");
    }

}
