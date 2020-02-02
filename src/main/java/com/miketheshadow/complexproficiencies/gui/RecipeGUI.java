package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class RecipeGUI extends GenericGUI
{
    public RecipeGUI(Player player, List<ItemStack> buildItems, String title,int size)
    {
        super(player, buildItems, title,size);
    }
}
