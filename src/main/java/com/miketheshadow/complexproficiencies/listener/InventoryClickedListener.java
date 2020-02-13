package com.miketheshadow.complexproficiencies.listener;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryClickedListener implements Listener
{
    @EventHandler
    public void InventorySlotClickedEvent(InventoryClickEvent event)
    {
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        if(crafter == null)return;
        if(event.getClickedInventory() == null) return;

        if(crafter.crafting)
        {
            boolean test = craftBuilder(event);
            event.setCancelled(test);
            return;
        }
        else
        {

            craftingEvent(event);
        }
        event.setCancelled(true);
    }

    public boolean craftBuilder(InventoryClickEvent event)
    {
        ItemStack itemClicked = event.getInventory().getItem(event.getSlot());
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());
        Player player  = (Player)event.getWhoClicked();
        if(crafter.itemType == null)
        {
            if(!event.getClick().isLeftClick() || event.getCursor() == null) return true;
            if(itemClicked == null) return true;
            crafter.transfer = true;
            crafter.currentGUI.itemBuilder((Player)event.getWhoClicked(),"BUILDER");
            crafter.itemType = itemClicked;
            return true;
        }
        if(itemClicked == null) return false;
        if(event.getClickedInventory().getType() != InventoryType.PLAYER && itemClicked.getType() == Material.GREEN_SHULKER_BOX)
        {
            if(!event.getClick().isLeftClick() || event.getCursor() == null)return false;
            Inventory inventory = event.getClickedInventory();
            ItemStack[] stack = inventory.getContents();
            if(stack[0] == null)
            {
                player.sendMessage("You are missing the item to craft!");
                return false;
            }
            if(inventory.getContents().length < 3)
            {
                player.sendMessage("You don't have enough items!");
                return false;
            }
            List<ItemStack> ingredients = new ArrayList<>();
            for (ItemStack item: inventory.getContents())
            {
                if(item != null)
                {
                    if(item.getType() != Material.GREEN_SHULKER_BOX && item.getType() != stack[0].getType())
                    {
                        ingredients.add(item);
                    }
                }

            }
            CustomRecipe customRecipe = new CustomRecipe(ingredients, NBTItem.convertItemtoNBT(stack[0]),crafter.currentGUI.levelReq,crafter.currentGUI.xpValue);
            customRecipe.xpGain = crafter.currentGUI.xpValue;
            customRecipe.levelReq = crafter.currentGUI.levelReq;
            Recipes.register(crafter.itemType.getItemMeta().getDisplayName(),customRecipe);
            if(itemClicked.getType() == Material.GREEN_SHULKER_BOX)
            {
                player.sendMessage("Item created!");
                ComplexProficiencies.crafters.remove(event.getWhoClicked().getUniqueId());
                player.closeInventory();
                Recipes.saveRecipes();
            }
        }
        return false;
    }



    public void craftingEvent(InventoryClickEvent event)
    {
        if(!event.getClick().isLeftClick() || event.getCursor() == null)return;
        if(event.getClickedInventory().getType().equals(InventoryType.PLAYER))return;
        ItemStack itemClicked = event.getInventory().getItem(event.getSlot());
        if(itemClicked == null)return;
        Crafter crafter = ComplexProficiencies.crafters.get(event.getWhoClicked().getUniqueId());

        if(crafter.itemType == null)
        {
            crafter.transfer = true;
            crafter.currentGUI.craftingList((Player)event.getWhoClicked(),itemClicked.getItemMeta().getDisplayName());
            crafter.itemType = itemClicked;
            crafter.recipes = Recipes.recipes.get(itemClicked.getItemMeta().getDisplayName());
        }
        else if(crafter.itemToCraft == null)
        {
            for (CustomRecipe recipe: crafter.recipes)
            {
                if(recipe.getItemToBeCrafted().getString("id").equals(NBTItem.convertItemtoNBT(itemClicked).getString("id")))
                {
                    crafter.transfer = true;
                    crafter.currentGUI.craftingInventory((Player)event.getWhoClicked(),recipe);
                    crafter.itemToCraft = itemClicked;
                    crafter.recipe = recipe;
                    break;
                }
            }
        }
        else if (NBTItem.convertItemtoNBT(itemClicked).getString("id").equals(crafter.recipe.getItemToBeCrafted().getString("id")))
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
