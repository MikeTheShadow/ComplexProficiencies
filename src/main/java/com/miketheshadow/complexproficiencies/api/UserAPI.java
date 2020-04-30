package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.DBHandlers.UserDBHandler;
import me.realized.duels.api.user.User;
import org.bukkit.entity.Player;

public class UserAPI {

    @Deprecated
    public static CustomUser getUser(Player player){
        return UserDBHandler.getPlayer(player);
    }

    public static boolean userHasLabor(Player player,int amount) {
        return UserDBHandler.getPlayer(player).getLabor() > amount;
    }

    public static void updateExperience(Player player,String prof,int amount) {
        UserDBHandler.getPlayer(player).addExperience(prof,amount,player);
    }

    public static void updateUser(CustomUser user){
        UserDBHandler.updatePlayer(user);
    }
}
