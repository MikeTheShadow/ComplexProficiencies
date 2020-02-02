package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class InventoryClickedEvent implements Listener
{
    @EventHandler
    public void InventorySlotClickedEvent(InventoryClickEvent event)
    {
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(crafter == null)return;
        craftingEvent(event);
        event.setCancelled(true);
    }

    public void craftingEvent(InventoryClickEvent event)
    {
        if(!event.getClick().isLeftClick() || event.getCursor() == null)return;
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(event.getClickedInventory() == null) return;
        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER))return;
        ItemStack itemClicked = event.getInventory().getItem(event.getSlot());
        if(itemClicked == null)return;
        if(crafter.itemType == null)
        {
            crafter.transfer = true;
            crafter.currentGUI.craftingList((Player)event.getWhoClicked(),itemClicked.getItemMeta().getDisplayName());
            crafter.itemType = itemClicked;
            crafter.recipes = Recipes.recipes.get(itemClicked.getItemMeta().getDisplayName());
        }
        else if(crafter.itemToCraft == null)
        {
            Bukkit.broadcastMessage("RECIPE: " + crafter.recipes.get(0).getItemToBeCrafted().name);

            for (CustomRecipe recipe: crafter.recipes)
            {
                if(recipe.getItemToBeCrafted().getTypeName().equals(itemClicked.getType().toString()))
                {
                    crafter.transfer = true;
                    crafter.currentGUI.craftingInventory((Player)event.getWhoClicked(),recipe);
                    crafter.itemToCraft = itemClicked;
                    crafter.recipe = recipe;
                    break;
                }
            }

        }
        else if(itemClicked.getType().toString().equals(crafter.recipe.getItemToBeCrafted().toItem().getType().toString()))
        {
            if(crafter.canCraft()) crafter.currentGUI.craftItem((Player)event.getWhoClicked(),crafter.recipe);
        }
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
