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
        if(!event.getClick().isLeftClick())return false;
        if(event.getCursor() == null)return false;

        for (Crafter crafter: ComplexProficiencies.players)
        {
            if(crafter.player == event.getWhoClicked())
            {
                if(event.getClickedInventory().getType().equals(InventoryType.PLAYER))return true;
                ItemStack itemStack = event.getInventory().getItem(event.getSlot());
                if(itemStack == null)return false;
                CustomRecipe recipe = WeaponsmithingGUI.recipes.get(0);
                if(crafter.itemType == null)
                {
                    crafter.transfer = true;
                    WeaponsmithingGUI.subWeaponInventory((Player)event.getWhoClicked(),itemStack);
                    crafter.itemType = itemStack;
                }
                else if(crafter.itemToCraft == null)
                {
                    crafter.transfer = true;
                    WeaponsmithingGUI.craftingInventory((Player)event.getWhoClicked(),recipe);
                    crafter.itemToCraft = itemStack;
                    crafter.recipe = recipe;
                }
                else if(crafter.canCraft())
                {
                    WeaponsmithingGUI.craftItem((Player)event.getWhoClicked(),recipe);
                }
                return true;
            }
        }
        return false;
    }


    //prevent issue with closing the UI
    @EventHandler
    public void inventoryClosedEvent(InventoryCloseEvent event)
    {

        for (Crafter c: ComplexProficiencies.players)
        {
            if(c.player == event.getPlayer())
            {
                if(c.transfer) { c.transfer = false; }
                else { ComplexProficiencies.players.removeIf(crafter -> (event.getPlayer() == crafter.player)); }
                return;
            }
        }
    }
    @EventHandler
    public void playerLeaveEvent(PlayerQuitEvent event)
    {
        ComplexProficiencies.players.removeIf(crafter -> event.getPlayer() == crafter.player);
    }


}
