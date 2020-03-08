package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.listener.CustomCommandListener;
import com.miketheshadow.complexproficiencies.listener.InventoryListener;
import com.miketheshadow.complexproficiencies.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin {
    public static final String[] profList = new String[]{"armorsmithing", "cooking", "farming", "fishing", "handicrafts", "leatherworking", "metalworking", "mining", "weaponsmithing"};

    //Create a singleton here.
    public ComplexProficiencies complexProficiencies;

    public ComplexProficiencies getInstance()
    {
        if(complexProficiencies == null) { this.complexProficiencies = this; }
        return complexProficiencies;
    }


    @Override
    public void onEnable() {
        getInstance();
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
