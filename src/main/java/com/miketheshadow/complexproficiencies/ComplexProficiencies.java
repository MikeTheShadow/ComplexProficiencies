package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.listener.CustomCommandListener;
import com.miketheshadow.complexproficiencies.listener.InventoryListener;
import com.miketheshadow.complexproficiencies.listener.PlayerJoinListener;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

//TODO make it so that the person who has eaten the most carrots gets nightvision

public class ComplexProficiencies extends JavaPlugin {
    public static final String[] profList = new String[]{"armorsmithing", "cooking", "farming", "fishing", "handicrafts", "leatherworking", "metalworking", "mining", "weaponsmithing"};

    //Create a singleton here.
    public static ComplexProficiencies complexProficiencies;

    public ComplexProficiencies getInstance()
    {
        if(complexProficiencies == null) { complexProficiencies = this; }
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

        //Start labor regen
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () ->
        {
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players)
            {
                int labor = user.getLabor();
                if(!(labor > 1995)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline())player.sendMessage(ChatColor.YELLOW + "You gained 5 labor!");
                    user.setLabor(labor + 5);
                    UserDBHandler.updatePlayer(user);
                }
            }
        }, 0L, 6000L);
    }

    @Override
    public void onDisable() {

    }

}
