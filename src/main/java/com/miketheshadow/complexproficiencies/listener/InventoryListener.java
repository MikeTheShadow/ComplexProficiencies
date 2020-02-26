package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTContainer;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void inventorySlotClickedEvent(InventoryClickEvent event) {
        //Check that the user is using my inventory
        Player player = (Player)event.getWhoClicked();
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        if(topInventory.contains(BaseCategories.nextPage))event.setCancelled(true);
        else return;

        //Now we know we're in the custom inventory. Check if they clicked an item
        ItemStack item = event.getCurrentItem();
        if(!event.getClick().isLeftClick() || item == null)return;

        //Locate the current directory the user is in using the hidden NBT tags on the page located in position 49
        NBTContainer compound = NBTItem.convertItemtoNBT(topInventory.getItem(49));
        String location = compound.getString("location");
        Bukkit.broadcastMessage(ChatColor.GOLD + "DEBUG COMPOUND: " + compound.toString());

    }

}
