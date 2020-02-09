package com.miketheshadow.complexproficiencies.listener;


import com.miketheshadow.complexproficiencies.utils.CustomPlayer;
import com.miketheshadow.complexproficiencies.utils.DBHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event)
    {
        DBHandler.checkPlayer(event.getPlayer());
        CustomPlayer player = DBHandler.getPlayer(event.getPlayer());
        player.addExperience("Weaponsmithing",50);
        DBHandler.updatePlayer(player);
    }
}
