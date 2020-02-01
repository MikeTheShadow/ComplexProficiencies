package com.miketheshadow.complexproficiencies.listeners;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.WeaponsmithingGUI;
import com.miketheshadow.complexproficiencies.crafting.recipe.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.proficiencies.Weaponsmithing;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        CustomRecipe recipe = WeaponsmithingGUI.recipes.get(0);
        if(crafter.itemType == null)
        {
            crafter.transfer = true;
            WeaponsmithingGUI.subWeaponInventory((Player)event.getWhoClicked(),itemClicked);
            crafter.itemType = itemClicked;
        }
        else if(crafter.itemToCraft == null)
        {
            crafter.transfer = true;
            WeaponsmithingGUI.craftingInventory((Player)event.getWhoClicked(),recipe);
            crafter.itemToCraft = itemClicked;
            crafter.recipe = recipe;
        }
        else if(itemClicked.equals(crafter.recipe.getItemToBeCrafted().toItem()))
        {
            if(crafter.canCraft()) WeaponsmithingGUI.craftItem((Player)event.getWhoClicked(),recipe);
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
