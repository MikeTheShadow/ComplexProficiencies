package com.miketheshadow.complexproficiencies.utils;

import com.miketheshadow.complexproficiencies.ComplexProficiencies;
import de.leonhard.storage.Json;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelConfig;
import static com.miketheshadow.complexproficiencies.ComplexProficiencies.levelMap;

public class ExperienceUtil
{

    public static void addPlayerExperience(CustomUser user, Player player, int addition, boolean mute) {

        float bonus = Float.parseFloat(ComplexProficiencies.expansion.onPlaceholderRequest(player,"boost_zero"));
        if(bonus < 2) bonus = 1;
        addition = Math.round(bonus * addition);
        int[] playerArray = user.getLevelXP();
        int currentLevel = playerArray[0];
        int totalExperience = playerArray[1] + addition;
        if(levelMap.get(currentLevel + 1) == null) {
            user.setLevelXP(new int[] {currentLevel,totalExperience});
            UserDBHandler.updatePlayer(user);
        } else {
            for(int i = 1;i < levelMap.size();i++) {

                if(totalExperience < levelMap.get(i)) {
                    currentLevel = i;
                    if(currentLevel > playerArray[0]) {
                        player.setLevel(currentLevel);
                        if(!mute)player.sendMessage(levelConfig.get("settings.levelup").toString().replace("%","" + currentLevel));
                        runLevelUpCommands(user,player.getLevel());
                    }
                    if(!mute)player.sendMessage(levelConfig.get("settings.experience").toString().replace("%","" + addition));

                    //update player data
                    user.setLevelXP(new int[] {currentLevel,playerArray[1] + addition});
                    UserDBHandler.updatePlayer(user);
                    return;
                } else if(i == levelMap.size() - 1) {
                    if(!mute)player.sendMessage(levelConfig.get("settings.levelup").toString().replace("%","" + (currentLevel + 1)));
                    player.setLevel(currentLevel + 1);
                    runLevelUpCommands(user,player.getLevel());
                    if(!mute)player.sendMessage(levelConfig.get("settings.experience").toString().replace("%","" + addition));
                    user.setLevelXP(new int[] {currentLevel + 1,playerArray[1] + addition});
                    UserDBHandler.updatePlayer(user);
                }
                totalExperience -= levelMap.get(i);
            }
        }
    }

    public static void setPlayerLevel(CustomUser user,int level) {

        int totalXP = 0;

        for(int i = 1;i < level;i++) {
            totalXP += levelMap.get(i);
        }
        user.setLevelXP(new int[] {level,totalXP});
        UserDBHandler.updatePlayer(user);
    }

    public static int getPlayerCurrentXP(CustomUser user) {
        int[] playerArray = user.getLevelXP();
        int currentLevel = playerArray[0];
        int totalExperience = playerArray[1];
        if(levelMap.get(currentLevel) == null) {
            user.setLevelXP(new int[] {currentLevel,totalExperience});
            UserDBHandler.updatePlayer(user);
        } else {
            for(int i = 1;i < levelMap.size();i++) {

                if(totalExperience < levelMap.get(i)) {
                    return totalExperience;
                }
                totalExperience -= levelMap.get(i);
            }
        }
        return -1;
    }

    public static void runLevelUpCommands(CustomUser user,int level)
    {
        int id = 1;
        String command =replaceTagsWithInformation(user,level,id);
        for(int i =0; i < 50;i++)
        {
            if(command == null || command.equalsIgnoreCase(""))break;
            id++;
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),command);
            command = replaceTagsWithInformation(user,level,id);
        }
    }

    public static String replaceTagsWithInformation(CustomUser user,int level,int id)
    {
        Json levelConf = levelConfig;
        String command = levelConf.getString(level + "." + id);
        if(command == null) return null;
        command = command.replaceAll("%level%",user.getLevelXP()[0] + "");
        command = command.replaceAll("%username%",user.getName());
        return command;
    }

}