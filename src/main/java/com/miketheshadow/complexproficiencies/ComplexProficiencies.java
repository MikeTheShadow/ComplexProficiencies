package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.listener.CustomCommandEvent;
import com.miketheshadow.complexproficiencies.listener.InventoryClickedEvent;
import com.miketheshadow.complexproficiencies.listener.ItemCraftedEvent;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    public static Map<UUID,Crafter> crafters = new HashMap<>();
    @Override
    public void onEnable()
    {
        Recipes.location = this.getDataFolder().getAbsolutePath() + "/";
        File file = new File(Recipes.location);
        if(!file.exists()) { file.mkdir(); }
        Recipes.loadRecipes();
        List<ItemStack> LIST = BaseCategories.getAllItems();
        //TODO remove this. Just used to register the items;
        //register events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ItemCraftedEvent(), this);
        pluginManager.registerEvents(new InventoryClickedEvent(), this);
        //register commands
        this.getCommand("craftinggui").setExecutor(new CustomCommandEvent(this));
        this.getCommand("getitemtype").setExecutor(new CustomCommandEvent(this));
        this.getCommand("addrecipe").setExecutor(new CustomCommandEvent(this));
    }
    @Override
    public void onDisable()
    {
        Recipes.saveRecipes();
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
