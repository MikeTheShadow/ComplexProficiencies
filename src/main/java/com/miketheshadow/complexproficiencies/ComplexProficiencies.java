package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.listener.*;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.XPBoostExpansion;
import de.leonhard.storage.Json;
import me.realized.duels.api.Duels;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

//TODO make it so that the person who has eaten the most carrots gets night-vision

public class ComplexProficiencies extends JavaPlugin {
    public static final String[] profList = new String[]{"armorsmithing", "cooking", "farming", "fishing", "handicrafts", "leatherworking", "metalworking", "mining", "weaponsmithing"};

    //Create a singleton here.
    public static ComplexProficiencies complexProficiencies;
    public static Duels duelsApi;
    public static XPBoostExpansion expansion;
    public static Json levelConfig;
    public static HashMap<Integer,Integer> levelMap;

    public ComplexProficiencies getInstance()
    {
        if(complexProficiencies == null) { complexProficiencies = this; }
        return complexProficiencies;
    }

    @Override
    public void onEnable() {

        //for adding stuff without breaking everything
        for(Document document : UserDBHandler.getAllDocuments()) {
            if(document.get("lastHP") == null) {
                document.append("lastHP",50D);
                UserDBHandler.updateDocument(document);
            }
        }



        loadLevelConfig();
        levelMap  = buildLevelMap();
        getInstance();
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        if(levelConfig.getBoolean("reset")) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "WARNING RESET MODE IS ENABLED! PLEASE DISABLE IN THE CONFIG IF THIS WAS NOT INTENDED!");
        }

        //Duels
        duelsApi = (Duels) Bukkit.getServer().getPluginManager().getPlugin("Duels");

        //register events
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        pluginManager.registerEvents(new InventoryListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new ModifyPlayerDamageListener(),this);
        pluginManager.registerEvents(new PlayerAttacksListener(),this);
        pluginManager.registerEvents(new PlayerXPListener(),this);
        //register prof commands
        this.getCommand("opengui").setExecutor(new CustomCommandListener(this));
        this.getCommand("getitemtags").setExecutor(new CustomCommandListener(this));
        this.getCommand("addrecipe").setExecutor(new CustomCommandListener(this));
        this.getCommand("addcategory").setExecutor(new CustomCommandListener(this));
        this.getCommand("removecategory").setExecutor(new CustomCommandListener(this));
        this.getCommand("addsubcategory").setExecutor(new CustomCommandListener(this));
        this.getCommand("resetdb").setExecutor(new CustomCommandListener(this));

        //register xp commmands
        this.getCommand("mystats").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("userstats").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("fixexperience").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("setexperience").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("setlevel").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("addexperience").setExecutor(new ExperienceCommandListener(this));

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Doing labor tick");
            List<CustomUser> players = UserDBHandler.getAllPlayers();
            for (CustomUser user : players) {
                int labor = user.getLabor();
                if(!(labor > 1990)){
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline())player.sendMessage(ChatColor.YELLOW + "You gained "
                            + ChatColor.GOLD
                            +"10"
                            + ChatColor.YELLOW
                            + " labor!"
                            + ChatColor.GRAY + "[" + ChatColor.GOLD + (labor + 10) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]");
                    user.setLabor(labor + 10);
                    UserDBHandler.updatePlayer(user);
                }
                else if(labor < 2000) {
                    Player player = Bukkit.getPlayer(user.getName());
                    if(player != null && player.isOnline())player.sendMessage(ChatColor.YELLOW + "You gained "
                            + ChatColor.GOLD
                            +"10"
                            + ChatColor.YELLOW
                            + " labor! "
                            + ChatColor.GRAY + "[" + ChatColor.GOLD + (2000 - labor) + ChatColor.GRAY + "/" + ChatColor.GOLD + "2000" + ChatColor.GRAY + "]");
                    user.setLabor(2000);
                    UserDBHandler.updatePlayer(user);
                }
            }
        }, 0L, 6000L);
    }

    public static HashMap<Integer,Integer> buildLevelMap() {
        if(levelMap != null) return levelMap;
        HashMap<Integer,Integer> map = new HashMap<>();
        int i = 1;
        int level = levelConfig.getInt("levels." + i);
        while (level != 0) {
            map.put(i,level);
            level = levelConfig.getInt("levels." + (i += 1));
        }
        return map;
    }

    public static void rebuildLevelMap() {
        HashMap<Integer,Integer> map = new HashMap<>();
        int i = 1;
        int level = levelConfig.getInt("levels." + i);
        while (level != 0) {
            map.put(i,level);
            level = levelConfig.getInt("levels." + (i += 1));
        }
    }

    @Override
    public void onDisable() {
        for (CustomUser user: UserDBHandler.getAllPlayers())
        {
            if(Objects.requireNonNull(Bukkit.getPlayer(user.getName())).isOnline()) {
                double health = Bukkit.getPlayer(user.getName()).getHealth();
                user.setLastHP(health);
                UserDBHandler.updatePlayer(user);
            }
        }
    }


    public void loadLevelConfig()
    {
        levelConfig = new Json("config", this.getDataFolder().getPath());

        //basic level stuff
        levelConfig.setDefault("levels.1", 100);
        levelConfig.setDefault("levels.2", 200);
        levelConfig.setDefault("levels.3", 300);
        levelConfig.setDefault("levels.4.", 400);
        levelConfig.setDefault("levels.5.", 500);

        levelConfig.setDefault("2.1","broadcast leveled %username% to level %level%");
        levelConfig.setDefault("2.2","broadcast you need more experience to level up!");
        levelConfig.setDefault("3.1","broadcast leveled %username% to level 2");
        levelConfig.setDefault("3.2","broadcast this is a second command woot!");
        expansion = new XPBoostExpansion();
        expansion.canRegister();

        levelConfig.setDefault("settings.experience","§8You gained §a % §8experience!");
        levelConfig.setDefault("settings.levelup","§6You leveled up to level %!");
        levelConfig.setDefault("settings.attackMessage", "§6The level difference is too high!");
        levelConfig.setDefault("settings.levelDifference", 5);
        levelConfig.setDefault("reset",false);
    }

}
