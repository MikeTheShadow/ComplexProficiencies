package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickedEvent implements Listener
{
    @EventHandler
    public void InventorySlotClickedEvent(InventoryClickEvent event)
    {
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(crafter == null)return;
        event.setCancelled(weaponSmithing(event));
        event.setCancelled(true);
    }

    public boolean weaponSmithing(InventoryClickEvent event)
    {
        if(!event.getClick().isLeftClick() || event.getCursor() == null)return false;
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER))return true;
        ItemStack itemClicked = event.getInventory().getItem(event.getSlot());
        if(itemClicked == null)return false;
        if(crafter.itemType == null)
        {
            crafter.transfer = true;
            crafter.currentGUI.craftingList((Player)event.getWhoClicked(),itemClicked.getItemMeta().getDisplayName());
            crafter.itemType = itemClicked;
            crafter.recipe = crafter.currentGUI.recipes.get(itemClicked.getItemMeta().getDisplayName()).get(0);
        }
        else if(crafter.itemToCraft == null)
        {
            crafter.transfer = true;
            crafter.currentGUI.craftingInventory((Player)event.getWhoClicked(),crafter.recipe);
            crafter.itemToCraft = itemClicked;
        }
        else if(itemClicked.equals(crafter.recipe.getItemToBeCrafted().toItem()))
        {
            if(crafter.canCraft()) crafter.currentGUI.craftItem((Player)event.getWhoClicked(),crafter.recipe);
        }
        return true;
    }


    //prevent issue with closing the UI
    @EventHandler
    public void inventoryClosedEvent(InventoryCloseEvent event)
    {
        Crafter crafter = ComplexProficiencies.crafters.get(event.getPlayer().getUniqueId());
        if(crafter == null)return;
        if(crafter.transfer) { crafter.transfer = false; }
        else { ComplexProficiencies.crafters.remove(event.getPlayer().getUniqueId()); }
    }
    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event)
    {
        Crafter crafter = ComplexProficiencies.crafters.get(event.getPlayer().getUniqueId());
        if(crafter != null)ComplexProficiencies.crafters.remove(event.getPlayer().getUniqueId());
    }


}
