package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import me.realized.duels.api.user.User;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class UserAPI {

    @Deprecated
    public static CustomUser getUser(Player player){
        return UserDBHandler.getPlayer(player);
    }

    public static boolean userHasLabor(Player player,int amount) {
        return UserDBHandler.getPlayer(player).getLabor() >= amount;
    }

    public static void updateUserProf(Player player, String prof, int amount) {
        UserDBHandler.getPlayer(player).addExperience(prof,amount,player);
    }

    public static void updateUser(CustomUser user){
        UserDBHandler.updatePlayer(user);
    }

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
        UserDBHandler.updatePlayer(user);
    }

    public static int getUserLabor(Player player) {
        return UserDBHandler.getPlayer(player).getLabor();
    }
}
