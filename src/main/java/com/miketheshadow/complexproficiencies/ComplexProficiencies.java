package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.Database.DBHandler;
import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.crafting.recipe.Recipes;
import com.miketheshadow.complexproficiencies.gui.BaseCategories;
import com.miketheshadow.complexproficiencies.listener.CustomCommandListener;
import com.miketheshadow.complexproficiencies.listener.InventoryClickedListener;
import com.miketheshadow.complexproficiencies.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    public static Map<UUID,Crafter> crafters = new HashMap<>();

    public static final String[] profList = new String[]{"armorsmithing","Cooking","Farming","Fishing","Handicrafts","Leatherworking","Metalworking","Mining","Weaponsmithing"};
    @Override
    public void onEnable()
    {
        Recipes.location = this.getDataFolder().getAbsolutePath() + "/";
        File file = new File(Recipes.location);
        if(!file.exists()) { file.mkdir(); }
        Recipes.loadRecipes();
        List<ItemStack> LIST = BaseCategories.getAllItems();

        buildDatabases();

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

    public static void buildDatabases()
    {
        File folder = new File(ComplexProficiencies.getPlugin(ComplexProficiencies.class).getDataFolder() + "\\proficiencies\\");
        if(!folder.exists())
        {
            if(!folder.mkdir())Bukkit.broadcastMessage(ChatColor.RED + "Cannot create folder!");
        }
        for (String prof: profList)
        {
            DBHandler.createDatabase(prof);
            DBHandler.createUserTable(prof);
        }
    }



}
