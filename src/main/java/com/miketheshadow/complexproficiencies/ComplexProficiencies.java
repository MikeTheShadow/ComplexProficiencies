/*
 *
 *  * Copyright 2019-2020 Michael Pape and contributors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.miketheshadow.complexproficiencies;


import com.miketheshadow.complexproficiencies.command.*;
import com.miketheshadow.complexproficiencies.command.base.*;
import com.miketheshadow.complexproficiencies.command.experience.*;
import com.miketheshadow.complexproficiencies.listener.*;
import com.miketheshadow.complexproficiencies.regrading.listener.OpenRegradeWindowListener;
import com.miketheshadow.complexproficiencies.regrading.listener.RegradeInventoryListener;
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
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

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
    public static String VERSION = "2.4.8";

    //economy
    public static Economy econ;

    public ComplexProficiencies setInstance() {
        if(INSTANCE == null) { INSTANCE = this; }
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        
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
        pluginManager.registerEvents(new OpenRegradeWindowListener(),this);
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
        new ResetLaborCommand();
        new ComplexAPICommand();
        new ComplexDataCommand();
        //xp plugin commands
        new MyStatsCommand();
        new UserStatsCommand();
        new FixExperienceCommand();
        new SetExperienceCommand();
        new SetLevelCommand();
        new AddExperienceCommand();
        new AddPartyExperienceCommand();
        //Only command that needs to be registered goes here
        new ComplexDebugCommand();
        //Disabled due to bugs

        /*
        try { registerCommands(); }
        catch (Exception e) {
            ComplexDebugCommand.error = "Error " + e.getMessage();
        }
         */


        //start labor thread
        LaborThread thread = new LaborThread();
        thread.start("Labor Thread");

    }

    private static void registerCommands() throws IllegalAccessException, InstantiationException {
        Reflections reflections = new Reflections("com.miketheshadow.complexproficiencies");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Command.class);
        Bukkit.getConsoleSender().sendMessage("Starting reflection");
        StringBuilder builder = new StringBuilder();
        builder.append("C: ");
        for (Class<?> c : classSet) {
            builder.append(c.getName()).append(" ");
            c.newInstance();
        }
        ComplexDebugCommand.error = builder.toString();
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
