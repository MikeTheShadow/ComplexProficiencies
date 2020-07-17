package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.command.*;
import com.miketheshadow.complexproficiencies.listener.*;
import com.miketheshadow.complexproficiencies.regrading.Grade;
import com.miketheshadow.complexproficiencies.regrading.command.RegradeCommand;
import com.miketheshadow.complexproficiencies.regrading.listener.RegradeInventoryListener;
import com.miketheshadow.complexproficiencies.regrading.listener.RightClickListener;
import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import com.miketheshadow.complexproficiencies.utils.LaborThread;
import com.miketheshadow.complexproficiencies.utils.XPBoostExpansion;
import de.leonhard.storage.Json;
import me.realized.duels.api.Duels;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Objects;

//TODO make it so that the person who has eaten the most carrots gets night-vision

public class ComplexProficiencies extends JavaPlugin {
    public static final String[] profList = new String[]{"armorsmithing", "cooking", "farming", "fishing", "handicrafts", "leatherworking", "metalworking", "mining", "weaponsmithing","larceny"};

    //Create a singleton here.
    public static ComplexProficiencies INSTANCE;
    public static Duels duelsApi;
    public static XPBoostExpansion expansion;
    public static Json levelConfig;
    public static HashMap<Integer,Integer> levelMap;

    //version
    public static String VERSION = "2.4.2";

    //economy
    public static Economy econ;

    public ComplexProficiencies setInstance() {
        if(INSTANCE == null) { INSTANCE = this; }
        return INSTANCE;
    }

    @Override
    public void onEnable() {

        for(String grade : Grade.gradeList) {
            Bukkit.getConsoleSender().sendMessage("DEBUG: " + grade);
        }

        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        loadLevelConfig();
        levelMap  = buildLevelMap();
        setInstance();
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
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new ModifyPlayerDamageListener(),this);
        pluginManager.registerEvents(new PlayerAttacksListener(),this);
        pluginManager.registerEvents(new PlayerXPListener(),this);
        pluginManager.registerEvents(new EntityDeathListener(),this);
        pluginManager.registerEvents(new PlayerVehicleListener(),this);
        pluginManager.registerEvents(new PlayerCraftListener(),this);
        //regrade listener
        pluginManager.registerEvents(new RightClickListener(),this);
        pluginManager.registerEvents(new RegradeInventoryListener(),this);
        //register prof commands
        new ResetDBCommand();
        new LaborCommand();
        new ProfCommand();
        new ProfTopCommand();
        new CaravanCreateCommand();
        new CaravanReturnCommand();
        new ComplexVersionCommand();
        new RemovePlayerCommand();
        new AddLaborCommand();
        //register regrading commands
        //new RegradeCommand(); //TODO decide if we need this or not
        //register xp commmands
        this.getCommand("mystats").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("userstats").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("fixexperience").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("setexperience").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("setlevel").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("addexperience").setExecutor(new ExperienceCommandListener(this));
        this.getCommand("addpartyexperience").setExecutor(new ExperienceCommandListener(this));
        //start labor thread
        LaborThread thread = new LaborThread();
        thread.start("Labor Thread");

    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
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
        for (CustomUser user: UserDBHandler.getAllPlayers()) {
            if(Objects.requireNonNull(Bukkit.getPlayer(user.getName())).isOnline()) {
                double health = Bukkit.getPlayer(user.getName()).getHealth();
                user.setLastHP(health);
                UserDBHandler.updatePlayer(user);
            }
        }
    }
    
    public void loadLevelConfig() {
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
