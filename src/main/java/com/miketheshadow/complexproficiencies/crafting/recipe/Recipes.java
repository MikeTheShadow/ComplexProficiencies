package com.miketheshadow.complexproficiencies.crafting.recipe;

import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Recipes
{
    public static HashMap<String,List<CustomRecipe>> recipes = new HashMap<>();

    public static CustomRecipe testRecipe()
    {
        List<CustomItem> itemList = new ArrayList<>();
        itemList.add(new CustomItem(16,"METALLURGY_BRONZE_INGOT",""));
        itemList.add(new CustomItem(1,"METALLURGY_KALENDRITE_INGOT",""));
        itemList.add(new CustomItem(4,"STICK",""));
        return new CustomRecipe(itemList, NBTItem.convertItemtoNBT(new ItemStack(Material.DIRT)));
    }
    public static void register(String location,CustomRecipe recipe)
    {
        recipes.get(location).add(recipe);
    }


}
