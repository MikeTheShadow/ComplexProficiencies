package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class GenericGUI
{
    public List<ItemStack> categoryList;
    public GenericGUI(Player player, List<ItemStack> buildItems, String title,int size)
    {
        categoryList = buildItems;
        //generate the options
        Inventory inventory = Bukkit.createInventory(player, size, title);
        for (int i = 0; i < categoryList.size() - 1; i++)
        {
            inventory.setItem(i, categoryList.get(i));
        }
        player.openInventory(inventory);
        ComplexProficiencies.crafters.put(player.getUniqueId(),new Crafter(player,this));
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
            inventory.addItem(recipe.getItemToBeCrafted().toItem());
        }
        player.openInventory(inventory);
    }

    public void craftingInventory(Player player,CustomRecipe recipe)
    {
        ItemStack stack = new ItemStack(Material.valueOf(recipe.getItemToBeCrafted().getTypeName()));
        Crafter crafter = ComplexProficiencies.crafters.get(player.getUniqueId());
        if(crafter == null)return;
        Inventory inventory = Bukkit.createInventory(player, 54, recipe.getItemToBeCrafted().getName());
        player.openInventory(inventory);
        inventory.setItem(0,stack);
        int i = 9;
        for (CustomItem item: recipe.getRequiredItems())
        {
            inventory.setItem(i,item.toItem());
            i++;
        }
    }
    public void craftItem(Player player, CustomRecipe recipe)
    {
        for (CustomItem item:recipe.getRequiredItems())
        {
            player.getInventory().removeItem(new ItemStack(Material.valueOf(item.getTypeName()),item.getAmount()));
        }
        player.getInventory().addItem(recipe.getItemToBeCrafted().toItem());
    }

}
