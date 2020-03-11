package com.miketheshadow.complexproficiencies.api;

import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.entity.Player;

public class UserAPI {

    public static CustomUser getUser(Player player){
        return UserDBHandler.getPlayer(player);
    }

    public static void updateUser(CustomUser user){
        UserDBHandler.updatePlayer(user);
    }
}
