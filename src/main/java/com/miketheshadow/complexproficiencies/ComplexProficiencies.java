package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.listener.CustomCommandListener;
import com.miketheshadow.complexproficiencies.listener.InventoryClickedListener;
import com.miketheshadow.complexproficiencies.listener.PlayerJoinListener;
import com.miketheshadow.complexproficiencies.utils.RecipeDBHandler;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    public static Map<UUID,Crafter> crafters = new HashMap<>();
    public static final String[] profList = new String[]{"Armorsmithing","Cooking","Farming","Fishing","Handicrafts","Leatherworking","Metalworking","Mining","Weaponsmithing"};

    @Override
    public void onEnable()
    {
        if(!this.getDataFolder().exists()) { this.getDataFolder().mkdir(); }
        UserDBHandler.init();
        RecipeDBHandler.init();
        List<ItemStack> LIST = BaseCategories.getAllItems();

        //register events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryClickedListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        //register commands
        this.getCommand("craftinggui").setExecutor(new CustomCommandListener(this));
        this.getCommand("getitemtags").setExecutor(new CustomCommandListener(this));
        this.getCommand("addrecipe").setExecutor(new CustomCommandListener(this));
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
