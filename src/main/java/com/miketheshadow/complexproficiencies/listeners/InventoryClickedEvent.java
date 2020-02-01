package com.miketheshadow.complexproficiencies.listeners;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.gui.GenericGUI;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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
        event.setCancelled(weaponSmithing(event));
    }

    public boolean weaponSmithing(InventoryClickEvent event)
    {
        if(!event.getClick().isLeftClick() || event.getCursor() == null)return false;
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(crafter == null)return false;
        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER))return true;
        ItemStack itemClicked = event.getInventory().getItem(event.getSlot());
        if(itemClicked == null)return false;

        CustomRecipe recipe = crafter.currentGUI.recipes.get(0);
        if(crafter.itemType == null)
        {
            crafter.transfer = true;
            GenericGUI.craftingList((Player)event.getWhoClicked(),itemClicked);
            crafter.itemType = itemClicked;
        }
        else if(crafter.itemToCraft == null)
        {
            crafter.transfer = true;
            GenericGUI.craftingInventory((Player)event.getWhoClicked(),recipe);
            crafter.itemToCraft = itemClicked;
            crafter.recipe = recipe;
        }
        else if(itemClicked.equals(crafter.recipe.getItemToBeCrafted().toItem()))
        {
            if(crafter.canCraft()) GenericGUI.craftItem((Player)event.getWhoClicked(),recipe);
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
