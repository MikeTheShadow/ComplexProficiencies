package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void inventorySlotClickedEvent(InventoryClickEvent event) {
        Player player = (Player)event.getWhoClicked();
        Inventory inventory = player.getOpenInventory().getTopInventory();
        if(inventory.contains(BaseCategories.nextPage))event.setCancelled(true);

    }

}
