package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.entity.Player;
import java.util.HashMap;

public class ProficiencyAPI {
    public static int getProfLevel(Player player,String profname) {
        CustomUser user = UserDBHandler.getPlayer(player);
        if(user.getProfessions() == null) return -1;
        return user.getLevelFromProf(profname);
    }

    public static HashMap<String, Integer> getAllProfs(Player player) {
        CustomUser user = UserDBHandler.getPlayer(player);
        if(user.getProfessions() == null) return null;
        return user.getProfessions();
    }

    public static void addExperienceToProf(Player player,String profname,int experienceAmount) {
        CustomUser user = UserDBHandler.getPlayer(player);
        user.addExperience(profname,experienceAmount,player);
    }
}
