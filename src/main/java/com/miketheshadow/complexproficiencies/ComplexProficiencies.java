package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.crafting.WeaponsmithingGUI;
import com.miketheshadow.complexproficiencies.crafting.recipe.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomItem;
import com.miketheshadow.complexproficiencies.crafting.recipe.CustomRecipe;
import com.miketheshadow.complexproficiencies.listeners.CustomCommandEvent;
import com.miketheshadow.complexproficiencies.listeners.InventoryClickedEvent;
import com.miketheshadow.complexproficiencies.listeners.ItemCraftedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    public static List<Crafter> players = new ArrayList<>();
    @Override
    public void onEnable()
    {
        List<CustomItem> itemList = new ArrayList<>();
        itemList.add(new CustomItem(16,"METALLURGY_BRONZE_INGOT",""));
        itemList.add(new CustomItem(1,"METALLURGY_KALENDRITE_INGOT",""));
        itemList.add(new CustomItem(4,"STICK",""));
        CustomRecipe recipe = new CustomRecipe(itemList,new CustomItem(1,"METALLURGY_BRONZE_SWORD","Bronze Sword"));
        WeaponsmithingGUI.recipes.add(recipe);
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
    public static Crafter getCrafter(Player player)
    {
        for (Crafter crafter: players)
        {
            if(crafter.player == player) return crafter;
        }
        return null;
    }

}
