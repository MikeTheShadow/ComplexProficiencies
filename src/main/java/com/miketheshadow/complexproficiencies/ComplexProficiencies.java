package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.listener.CustomCommandEvent;
import com.miketheshadow.complexproficiencies.listener.InventoryClickedEvent;
import com.miketheshadow.complexproficiencies.listener.ItemCraftedEvent;
import com.miketheshadow.complexproficiencies.utils.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    public static Map<UUID,Crafter> crafters = new HashMap<>();
    @Override
    public void onEnable()
    {
        //TODO remove this. Just used to register the items;
        List<ItemStack> stacks = BaseCategories.weaponsmithingItems();
        Recipes.register(BaseCategories.SWORD.getItemMeta().getDisplayName(),Recipes.fakeRecipe());
        //register events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ItemCraftedEvent(), this);
        pluginManager.registerEvents(new InventoryClickedEvent(), this);
        //register commands
        this.getCommand("weaponsmithinggui").setExecutor(new CustomCommandEvent(this));
        this.getCommand("getitemtype").setExecutor(new CustomCommandEvent(this));
    }
    @Override
    public void onDisable()
    {

    }
    public static Crafter getCrafter(UUID uuid)
    {
        if(crafters.get(uuid) == null)
        {
            return null;
        }
        return crafters.get(uuid);
    }

}
