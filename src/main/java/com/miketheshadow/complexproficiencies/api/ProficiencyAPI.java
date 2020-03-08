package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ProficiencyAPI
{
    public static int getProfLevel(Player player,String profname)
    {
        CustomUser user = UserDBHandler.getPlayer(player);
        return user.getProfessions().get(profname);
    }

    public static HashMap<String, Integer> getAllProfs(Player player)
    {
        CustomUser user = UserDBHandler.getPlayer(player);
        return user.getProfessions();
    }

    public static void updateProf(Player player,String profname,int experienceAmount)
    {
        CustomUser user = UserDBHandler.getPlayer(player);
        user.addExperience(profname,experienceAmount,player);
    }
}
