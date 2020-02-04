package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class GenericGUI
{
    public List<ItemStack> categoryList;
    public GenericGUI(Player player, List<ItemStack> buildItems, String title,int size,boolean isfalse)
    {
        categoryList = buildItems;
        //generate the options
        Inventory inventory = Bukkit.createInventory(player, size, title);
        for (int i = 0; i < categoryList.size(); i++)
        {
            inventory.setItem(i, categoryList.get(i));
        }
        player.openInventory(inventory);
        Crafter crafter = new Crafter(player,this);
        crafter.crafting = isfalse;
        ComplexProficiencies.crafters.put(player.getUniqueId(),crafter);
    }

    public void craftingList(Player player, String itemName)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, itemName);
        if(Recipes.recipes.get(itemName).isEmpty())
        {
            player.openInventory(inventory);
            return;
        }
        for (CustomRecipe recipe: Recipes.recipes.get(itemName))
        {
            inventory.addItem(NBTItem.convertNBTtoItem(recipe.getItemToBeCrafted()));
        }
        player.openInventory(inventory);
    }

    public void craftingInventory(Player player,CustomRecipe recipe)
    {
        ItemStack stack = NBTItem.convertNBTtoItem(recipe.getItemToBeCrafted());
        Crafter crafter = ComplexProficiencies.crafters.get(player.getUniqueId());
        if(crafter == null)return;
        Inventory inventory = Bukkit.createInventory(player, 54, recipe.getItemToBeCrafted().getString("id"));
        player.openInventory(inventory);
        inventory.setItem(0,stack);
        int i = 9;
        for (ItemStack item: recipe.getRequiredItems())
        {
            inventory.setItem(i,item);
            i++;
        }
    }
    public void craftItem(Player player, CustomRecipe recipe)
    {
        for (ItemStack item:recipe.getRequiredItems())
        {
            player.getInventory().removeItem(item);
        }
        player.getInventory().addItem(NBTItem.convertNBTtoItem(recipe.getItemToBeCrafted()));
    }

    public void itemBuilder(Player player,String guiName)
    {
        Inventory inventory = Bukkit.createInventory(player, 54, guiName);
        inventory.setItem(53,BaseCategories.register(Material.GREEN_SHULKER_BOX.toString(),"CREATE"));
        player.openInventory(inventory);
    }

}
