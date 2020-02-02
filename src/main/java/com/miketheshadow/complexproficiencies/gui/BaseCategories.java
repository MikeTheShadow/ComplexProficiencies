package com.miketheshadow.complexproficiencies.gui;

import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BaseCategories
{

    public static final ItemStack SWORD = register("TCONSTRUCT_BROADSWORD","SWORDS");
    public static final ItemStack GREATSWORD = register("TCONSTRUCT_CLEAVER","GREATSWORDS");
    public static final ItemStack RAPIER = register("TCONSTRUCT_RAPIER","RAPIERS");
    public static final ItemStack DAGGER = register("METALLURGY_SILVER_SWORD","DAGGERS");
    public static final ItemStack AXE = register("TCONSTRUCT_HATCHET","AXES");
    public static final ItemStack HAMMER = register("TCONSTRUCT_HAMMER","HAMMERS");
    public static final ItemStack SPEAR = register("TCONSTRUCT_ARROW_HEAD","SPEARS");

    public static List<ItemStack> weaponsmithingItems()
    {
        List<ItemStack> itemStack = new ArrayList<>();
        itemStack.add(SWORD);
        itemStack.add(GREATSWORD);
        itemStack.add(RAPIER);
        itemStack.add(DAGGER);
        itemStack.add(AXE);
        itemStack.add(HAMMER);
        itemStack.add(SPEAR);
        return itemStack;
    }

    public static ItemStack register(String itemStack,String name)
    {
        List<String> list = new ArrayList<>();
        list.add("Click to open the " + name.toLowerCase() + " menu.");
        ItemStack item = new ItemStack(Material.valueOf(itemStack));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + name);
        meta.setLore(list);
        item.setItemMeta(meta);

        List<CustomRecipe> crep = new ArrayList<>();
        Recipes.recipes.put( meta.getDisplayName(),crep);
        List<CustomRecipe> recipe =Recipes.recipes.get(meta.getDisplayName());
        return item;
    }
}
