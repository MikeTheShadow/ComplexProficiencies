package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.listeners.ItemCraftedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new ItemCraftedEvent(), this);
    }
    @Override
    public void onDisable()
    {

    }
}
