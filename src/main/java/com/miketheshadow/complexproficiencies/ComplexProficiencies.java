package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.crafting.Crafter;
import com.miketheshadow.complexproficiencies.listener.CustomCommandListener;
import com.miketheshadow.complexproficiencies.listener.InventoryListener;
import com.miketheshadow.complexproficiencies.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin {
    public static final String[] profList = new String[]{"Armorsmithing", "Cooking", "Farming", "Fishing", "Handicrafts", "Leatherworking", "Metalworking", "Mining", "Weaponsmithing"};
    public static Map<UUID, Crafter> crafters = new HashMap<>();

    @Override
    public void onEnable() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        //register events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);

        //register commands
        this.getCommand("opengui").setExecutor(new CustomCommandListener(this));
        this.getCommand("getitemtags").setExecutor(new CustomCommandListener(this));
        this.getCommand("addrecipe").setExecutor(new CustomCommandListener(this));
        this.getCommand("addcategory").setExecutor(new CustomCommandListener(this));
        this.getCommand("removecategory").setExecutor(new CustomCommandListener(this));
        this.getCommand("addsubcategory").setExecutor(new CustomCommandListener(this));
    }

    @Override
    public void onDisable() {

    }

}
