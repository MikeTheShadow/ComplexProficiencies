package com.miketheshadow.complexproficiencies.listener;


import com.miketheshadow.complexproficiencies.utils.CustomUser;
import com.miketheshadow.complexproficiencies.utils.UserDBHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class PlayerJoinListener implements Listener
{

    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoinEvent(PlayerJoinEvent event)
    {
        //Check if player is in database
        Player player = event.getPlayer();
        CustomUser user = UserDBHandler.getUserByID(player.getUniqueId().toString());
        if(user == null) { UserDBHandler.insertNewUser(player.getName(),player.getUniqueId().toString()); }
    }
}
